package com.connect.android.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.connect.android.client.model.*
import com.connect.android.client.modules.viewModelsModule
import com.connect.android.client.tools.servicesModule
import org.koin.core.context.startKoin

class ConnectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    viewModelsModule
                )
            )
        }
    }
}