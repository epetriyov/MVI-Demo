package com.connect.android.client

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class ConnectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}