package com.connect.android.client.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun View.color(@ColorRes res: Int) = ContextCompat.getColor(context, res)
fun View.textString(@StringRes res: Int): String = resources.getString(res)
fun View.textString(@StringRes res: Int, vararg args: Any): String = resources.getString(res, *args)
fun View.dimen(@DimenRes res: Int): Float = resources.getDimension(res)
fun View.drawable(@DrawableRes res: Int): Drawable? = ContextCompat.getDrawable(context, res)

fun Context.color(@ColorRes res: Int) = ContextCompat.getColor(this, res)
fun Context.textString(@StringRes res: Int): String = resources.getString(res)
fun Context.textString(@StringRes res: Int, vararg args: Any): String = resources.getString(res, *args)
fun Context.dimen(@DimenRes res: Int): Float = resources.getDimension(res)
fun Context.drawable(@DrawableRes res: Int): Drawable? = ContextCompat.getDrawable(this, res)