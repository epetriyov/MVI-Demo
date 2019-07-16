package com.connect.android.client.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.forEach
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