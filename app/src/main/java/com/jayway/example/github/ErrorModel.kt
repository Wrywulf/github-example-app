package com.jayway.example.github

import androidx.annotation.Keep
import java.io.IOException

/**
 * Models the errors in this application. It contains an error code unique across this application,
 * may contain a [Throwable] cause and error title and message.
 *
 * Used as the container for failure data in [Result.Failure].
 *
 * All [Throwable] instances can be mapped to this without exception (pun intended), but some may
 * yield identical error codes. Ie. MyObscureException1 and RareException may map to the same error
 * code if differentiation is deemed unnecessary.
 */
@Keep
data class ErrorModel(

    /**
     * Unique across this domain.
     */
    val code: Int,

    /**
     * User-friendly title.
     */
    val title: String? = null,

    /**
     * User-friendly message.
     */
    val message: String? = null,

    /**
     * Source [Throwable] that caused this error.
     */
    val throwable: Throwable? = null) {


    companion object {

        /**
         * Creates an [ErrorModel] from a given [Throwable]. Some [Throwable]s may result in models
         * with identical error codes if differentiation is deemed unnecessary
         *
         * @param throwable the [Throwable] to map
         * @return an [ErrorModel] containing the error code for the [throwable] and the [throwable] itself
         */
        operator fun invoke(throwable: Throwable): ErrorModel {
            return create(throwable)
        }

        private fun create(throwable: Throwable): ErrorModel {
            val code: Int = when (throwable) {
            /**
             * -1000..-1999 : IO-related exceptions
             * -2000..-2999 : Illegal, unexpected data content/structure
             * -9000..-9998 : Misc
             * -9999        : The fall-back error code
             */
            //FIXME currently the order matters, since we're basically checking "isAssignableFrom"
                is IOException                           -> -1000
                else                                     -> -9999 // all the unspecified exceptions end here
            }
            return ErrorModel(code = code, throwable = throwable)
        }
    }
}

