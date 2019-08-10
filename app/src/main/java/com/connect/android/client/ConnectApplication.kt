package com.connect.android.client

import android.app.Application
import com.connect.android.client.model.*
import com.connect.android.client.modules.*
import com.connect.android.client.tools.servicesModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
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
            androidContext(this@ConnectApplication)
            modules(
                listOf(
                    servicesModule,
                    authModule,
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
                    recommendationsView,
                    messagesView,
                    profileView,
                    myProfileView,
                    eventsView,
                    contactsView,
                    chatsView
                )
            )
        }
    }
}