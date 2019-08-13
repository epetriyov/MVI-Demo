package com.connect.android.client.extensions

import io.reactivex.Observable
import timber.log.Timber

fun Throwable.safeMessage(): String {
    return message ?: toString()
}

fun <T> Observable<T>.onLoggableError(onError: (kotlin.Throwable) -> T): Observable<T> {
    return doOnError { Timber.e(it) }.onErrorReturn(onError)
}