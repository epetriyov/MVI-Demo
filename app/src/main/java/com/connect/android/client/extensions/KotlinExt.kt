package com.connect.android.client.extensions

object Do {
    infix fun <T> exhaustive(any: T?) = any
}

inline fun <reified T> Any?.castOrNull() = this as? T