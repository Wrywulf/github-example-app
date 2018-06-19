package com.pensiondanmark.dinpension2.common.extensions

import android.view.View
import android.view.ViewGroup

/**
 * A convenience variable that sets the [View] to [View.VISIBLE] or [View.INVISIBLE] via a boolean
 */
var View.visible: Boolean
    get() = this.visibility == View.VISIBLE
    set(visible) = if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }

/**
 * Access the [ViewGroup] children as an array
 */
operator fun ViewGroup.get(pos: Int): View = getChildAt(pos)
