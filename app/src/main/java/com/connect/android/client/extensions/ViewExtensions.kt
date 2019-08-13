package com.connect.android.client.extensions

import android.app.Activity
import android.graphics.Point
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: Int, actionName: Int? = null, actionListener: View.OnClickListener? = null) {
    showSnackbar(context.getString(message), actionName?.let { context.getString(it) }, actionListener)
}

fun View.showSnackbar(message: String, actionName: String? = null, actionListener: View.OnClickListener? = null) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .apply {
            actionListener?.let { setAction(actionName, actionListener) }
        }.show()
}

fun View.showSnackbar(view: View) {
    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    (snackbar.view as Snackbar.SnackbarLayout).apply {
        forEach {
            it.visibility = View.INVISIBLE
        }
        setPadding(0, 0, 0, 0)
        addView(view, 0)
    }
    snackbar.show()
}

fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard(inputMethodManager: InputMethodManager) {
    if (this.requestFocus()) {
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }
}

fun TextView.setTextOrGone(value: String?) {
    if (value.isNullOrEmpty()) {
        isVisible = false
    } else {
        isVisible = true
        text = value
    }
}

fun Activity.getScreenWidth(): Int {
    val point = Point()
    windowManager.defaultDisplay.getSize(point)
    return point.x
}