package com.jayway.example.github.extensions

import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.KClass

/**
 * Prints the [Throwable]s stack trace to a [String] and returns it
 */
fun Throwable.stacktraceAsString(): String {
    val result = StringWriter()
    this.printStackTrace(PrintWriter(result))
    return result.toString()
}

/**
 * Checks if this [Throwable] itself or any of its causes are contained in the set of
 * [exceptions] provided
 *
 * @param exceptions the [Set] of [KClass]<[Throwable]> to check against
 * @return true if this [Throwable] [KClass] or any of its causes [KClass] are contained in [exceptions]
 */
fun Throwable.isItselfOrCauseContainedIn(exceptions: Set<KClass<out Throwable>>): Boolean {
    var throwable: Throwable? = this
    while (throwable != null) {
        Timber.v("Examining cause: ${throwable::class} against $exceptions")
        if (exceptions.contains(throwable::class)) {
            return true
        }
        throwable = throwable.cause
    }
    return false
}
