package com.connect.android.client.extensions

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.putWithClassNameKey(parcelable: T?) {
    parcelable?.let { putParcelable(T::class.java.simpleName, parcelable) }
}

inline fun <reified T : Parcelable> Bundle.getWithClassNameKey(): T? {
    return getParcelable(T::class.java.simpleName)
}