package com.connect.android.client

import android.app.Application
import com.connect.android.client.model.*
import com.connect.android.client.modules.recommendationsView
import com.connect.android.client.modules.viewModelsModule
import com.connect.android.client.modules.viewModule
import com.connect.android.client.tools.servicesModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class ConnectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        Fabric.with(this, Crashlytics())
        JodaTimeAndroid.init(this)
        startKoin {
            modules(
                listOf(
                    servicesModule,
                    authModule,
                    chatModule,
                    chatsModule,
                    commonModule,
                    contactsModule,
                    educationsModule,
                    eventsModule,
                    locationModule,
                    messagesModule,
                    profileModule,
                    recommendationsModule,
                    worksModule,
                    viewModelsModule,
                    viewModule,
                    recommendationsView
                )
            )
        }
    }
}