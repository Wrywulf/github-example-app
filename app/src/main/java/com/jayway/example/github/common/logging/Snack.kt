package com.jayway.example.github.common.logging

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * A wrapper around most commonly used [Snackbar] functionality. The default implementation does nothing.
 *
 * In order to actually delegate to a [Snackbar] implementation, one must install an implementation of
 * [SnackBarWrapper] using [install]. The
 */
class Snack {

    companion object {
        @Volatile
        var implementation: SnackBarWrapper = NoOpSnackBarWrapper()

        /**
         * Install (override) the default implementation, which does nothing. [RealSnackBarWrapper]
         * will delegate to the actual [Snackbar]
         */
        @JvmStatic
        fun install(snackBarWrapper: SnackBarWrapper) {
            implementation = snackBarWrapper
        }

        /**
         * Show a [Snackbar] given the provided arguments
         */
        @JvmStatic
        fun show(view: View, @StringRes resId: Int, duration: Int) : Snackbar? {
            return implementation.show(view, resId, duration)
        }

        /**
         * Show a [Snackbar] given the provided arguments
         */
        @JvmStatic
        fun show(view: View, text: CharSequence, duration: Int) : Snackbar? {
            return implementation.show(view, text, duration)
        }
    }

    abstract class SnackBarWrapper {
        abstract fun show(view: View, @StringRes resId: Int, duration: Int): Snackbar?
        abstract fun show(view: View, text: CharSequence, duration: Int): Snackbar?

    }

    private class NoOpSnackBarWrapper : SnackBarWrapper() {
        override fun show(view: View, resId: Int, duration: Int): Snackbar? {
            return null
        }

        override fun show(view: View, text: CharSequence, duration: Int): Snackbar? {
            return null
        }
    }

    /**
     * An implementation of [SnackBarWrapper] that delegates to a [Snackbar] implementation
     */
    class RealSnackBarWrapper : SnackBarWrapper() {
        override fun show(view: View, resId: Int, duration: Int) : Snackbar {
            return Snackbar.make(view, resId, duration).let {
                it.show()
                it
            }
        }

        override fun show(view: View, text: CharSequence, duration: Int) : Snackbar {
            return Snackbar.make(view, text, duration).let {
                it.show()
                it
            }
        }
    }
}
