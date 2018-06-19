package com.jayway.example.github

import androidx.annotation.Keep
import com.jayway.example.github.extensions.stacktraceAsString
import io.reactivex.Observable

/**
 * Wraps a [Success] or [Failure] to allow easier handling in streams etc.
 */
@Keep
sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure<out T>(val value: ErrorModel) : Result<T>() {
        constructor(throwable: Throwable) : this(ErrorModel(throwable))

        override fun toString(): String {
            return super.toString()
                .apply {
                    if (value.throwable != null) {
                        plus("\nFailure caused by : ${value.throwable.stacktraceAsString()}")
                    }
                }
        }
    }
}


/**
 * It is often the case that a [Result] is checked if instance of [Result.Success] or [Result.Failure]
 * and only actually doing a mapping on the [Result.Success.value], leaving the [Result.Failure.value] untouched (pass-through).
 *
 * This extension function eliminates all the boiler plate of the [Result.Failure] branch
 */
inline fun <T, R> Result<T>.mapSuccess(successMapper: (success: T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> {
            Result.Success(successMapper(this.value))
        }
        is Result.Failure -> {
            Result.Failure(this.value)
        }
    }
}

/**
 * Executes the provided lambda if the [Result] is [Result.Success] only
 */
inline fun <T, R> Result<T>.doSuccess(successHandler: (successValue: T) -> R) {
    when (this) {
        is Result.Success -> {successHandler(this.value)}
    }
}

/**
 * Executes the provided lambda if the [Result] is [Result.Failure] only
 */
inline fun <T> Result<T>.doFailure(failureHandler: (failureValue: ErrorModel) -> Unit) {
    when (this) {
        is Result.Failure -> {failureHandler(this.value)}
    }
}


/**
 * Similar to [Result.mapSuccess], but for [Observable] chains.
 *
 * Often, an [Observable] chain needs to be added based on the [Result.Success] and [Result.Failure] value emitted
 * from the upstream observable.
 *
 * This extension function eliminates all the boiler plate of the [Result.Failure] branch (provides a default lambda),
 * and makes it really simple to add success branch, just by specifying a lambda.
 */
inline fun <T, R> Observable<Result<T>>.switchMapSuccess(crossinline failureMapper: (errorModel: ErrorModel) -> Observable<Result<R>> =
                                                             { Observable.just(Result.Failure(it)) },
                                                         crossinline successMapper: (success: T) -> Observable<Result<R>>): Observable<Result<R>> {
    return this.switchMap {
        when (it) {
            is Result.Success -> {
                successMapper(it.value)
            }
            is Result.Failure -> {
                failureMapper(it.value)
            }
        }
    }
}