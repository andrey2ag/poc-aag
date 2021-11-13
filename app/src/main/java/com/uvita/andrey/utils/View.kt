package com.uvita.andrey.utils

import android.view.LayoutInflater
import android.view.View

// Visibility Helpers --------------------------------------------------------------------------------------------------

/**
 * Determine if the view is visible.
 */
fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

/**
 * Changes the visibility of the View to [View.GONE]
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Changes the visibility of the View to [View.GONE]
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * Changes the visibility of the View to [View.INVISIBLE]
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Toggles the visibility of the View depending on the [visible] flag, invoking the [visibleAction] if the flag is true,
 * or invoking the optional [goneAction] if the flag is false.
 */
@JvmOverloads
fun <T : View> T.toggleVisibilityWithAction(
    visible: Boolean,
    visibleAction: (T).() -> Unit,
    goneAction: (T).() -> Unit = {}
) {

    if (visible) {
        visible()
        visibleAction.invoke(this)
    } else {
        gone()
        goneAction.invoke(this)
    }
}

// Inflation Helpers ---------------------------------------------------------------------------------------------------

/**
 * Get a LayoutInflater with the context of the current [View].
 */
fun View.getLayoutInflater(): LayoutInflater {
    return LayoutInflater.from(this.context)
}
