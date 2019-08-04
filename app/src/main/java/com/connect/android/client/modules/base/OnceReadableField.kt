package com.connect.android.client.modules.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * c
 */
typealias ORField<T> = OnceReadableField<T>

@Parcelize
data class OnceReadableField<T>(private val value: @RawValue T?, private var updateField: Boolean = true) : Parcelable {

    companion object {
        fun <T> empty(): OnceReadableField<T> =
            OnceReadableField(null, false)
    }

    /**
     * Returns the value and prevents its use again.
     */
    private fun getContentIfNotHandled(): T? {
        return if (updateField) {
            updateField = false
            value
        } else {
            null
        }
    }

    fun bind(bindToView: (T) -> Unit) {
        getContentIfNotHandled()?.let { bindToView(it) }
    }

    /**
     * Returns the value, even if it's already been handled.
     */
    fun peekContent(): T? = value

    fun isEmpty() = value == null

}

fun <T> T?.withUpdate(): OnceReadableField<T> =
    OnceReadableField(this, true)

fun <T> T?.withoutUpdate(): OnceReadableField<T> =
    OnceReadableField(this, false)
