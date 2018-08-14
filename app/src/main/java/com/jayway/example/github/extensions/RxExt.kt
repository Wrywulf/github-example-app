package com.jayway.example.github.extensions

import com.jayway.example.github.ErrorModel
import com.jayway.example.github.Result
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.pow
import kotlin.reflect.KClass

/**
 * Repeats subscriptions to the single source if it emits a [Result.Failure] with a [java.io.IOException]
 * as the direct failure [Throwable] or as one of its causes
 *
 * @param maxRetries the optional maximum count of retries (excluding the initial call) that should be performed. Default is 3
 * @param delayLambda the optional delay lambda providing a delay as a [Pair] of [Long] and [TimeUnit].
 * Defaults to 3^retryCount seconds (3, 9, 27 .. )
 * @see [retryOnFailure]
 */
fun <T> Single<Result<T>>.retryIOExceptions(maxRetries: Int = 3,
                                            delayLambda: (retryCount: Int) -> Pair<Long, TimeUnit> = { retryCount ->
                                                Pair(3F.pow(retryCount).toLong(),
                                                     TimeUnit.SECONDS)
                                            }): Single<Result<T>> {
    return this.retryOnFailure(kotlin.collections.setOf(
//        retrofit2.HttpException::class, // FIXME until we rely on Retrofit
                                                        java.io.IOException::class),
                               maxRetries,
                               delayLambda)
}

@Suppress("MoveLambdaOutsideParentheses")
    /**
 * Repeats subscriptions to the single source if it emits a [Result.Failure] with a [Throwable] which
 * itself or one of its causes are contained in the provided [exceptions] [Set].
 *
 * @param exceptions a [Set] containing the [Throwable] classes that are retriable
 * @param maxRetries the optional maximum count of retries (excluding the initial call) that should be performed. Default is 3
 * @param delayLambda the optional delay lambda providing a delay as a [Pair] of [Long] and [TimeUnit].
 * Defaults to 3^retryCount seconds (3, 9, 27 .. )
 */
fun <T> Single<Result<T>>.retryOnFailure(exceptions: Set<KClass<out Throwable>> = emptySet(),
                                         maxRetries: Int = 3,
                                         delayLambda: (retryCount: Int) -> Pair<Long, TimeUnit> = { retryCount ->
                                             Pair(3F.pow(retryCount).toLong(), TimeUnit.SECONDS)
                                         }): Single<Result<T>> {
    return Single.defer({
                            // we need to hold the state saying whether we could retry or not.
                            // Wrapping it in Single.defer() confines it to each subscriber
                            val isRetriableFailure = AtomicBoolean()
                            this
                                .onErrorReturn { Result.Failure(ErrorModel(it)) }
                                .doOnSuccess { result ->
                                    var retriable = false
                                    Timber.v("Source emits: $result")
                                    if (result is Result.Failure) {
                                        // check if exception is something that should be retried (part of set)
                                        retriable = exceptions.isEmpty() || result.value.throwable?.isItselfOrCauseContainedIn(
                                            exceptions) ?: false
                                        Timber.v("Source emits: $result, isRetriableFailure: $retriable")
                                    }
                                    isRetriableFailure.set(retriable)
                                }.repeatWhen { signal ->
                                    signal
                                        .map { isRetriableFailure.get() }
                                        .takeWhile { it }
                                        .zipWith(Flowable.range(1, maxRetries),
                                                 BiFunction<Any, Int, Int> { _, retryCount -> retryCount })
                                        .flatMap { retryCount: Int ->
                                            // Exponential back-off
                                            val (delay, timeUnit) = delayLambda(retryCount)
                                            Timber.v("Retrying again after $delay $timeUnit..")
                                            Flowable.timer(delay, timeUnit)
                                        }
                                        .doOnNext {
                                            Timber.v("Retrying now")
                                        }
                                        .doOnTerminate {
                                            Timber.v("No more retries")
                                        }
                                }
                                .takeLast(1)
                                .singleOrError()
                                .onErrorReturn { Result.Failure(ErrorModel(it)) } // these are obviously not retriable :)
                        })
}

