package com.connect.android.client.extensions

import android.content.res.Resources

/**
 * Converts dp to pixel
 */
val Float.dpToPx: Float get() = (this * Resources.getSystem().displayMetrics.density)
val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts pixel to dp
 */
val Float.pxToDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.pxToDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()