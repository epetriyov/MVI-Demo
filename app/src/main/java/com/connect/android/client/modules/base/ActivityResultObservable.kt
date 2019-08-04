package com.connect.android.client.modules.base

interface ActivityResultObservable {

    fun addOnActivityResultListener(onActivityResultListener: ActivityResultListener)

    fun removeOnActivityResultListener(onActivityResultListener: ActivityResultListener)
}