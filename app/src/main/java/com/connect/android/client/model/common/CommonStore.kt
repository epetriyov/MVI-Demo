package com.connect.android.client.model.common

interface CommonStore {

    fun isFirstRun(): Boolean

    fun setFirstRun()

    fun resetFirstRun()
}