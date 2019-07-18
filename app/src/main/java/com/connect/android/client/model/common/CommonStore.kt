package com.connect.android.client.model.common

import android.content.SharedPreferences

interface CommonStore {

    fun isFirstRun(): Boolean

    fun setFirstRun()

    fun resetFirstRun()
}

class SharedCommonStore(private val sharedPreferences: SharedPreferences) : CommonStore {
    override fun isFirstRun(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFirstRun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetFirstRun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

