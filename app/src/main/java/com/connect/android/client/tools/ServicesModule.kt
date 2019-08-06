package com.connect.android.client.tools

import android.content.Context
import androidx.room.Room
import com.connect.android.client.BuildConfig
import com.connect.android.client.model.auth.SharedTokenStore
import com.connect.android.client.model.auth.TokenStore
import com.connect.android.client.tools.Constants.DB_NAME
import com.connect.android.client.tools.moshi.LENIENT_FACTORY
import com.connect.android.client.tools.moshi.MoshiDateTimeConverter
import com.connect.android.client.tools.okhttp.AuthInterceptor
import com.connect.android.client.tools.okhttp.TokenProvider
import com.connect.android.client.tools.room.ConnectDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.patloew.rxlocation.RxLocation
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Constants {
    const val PREF_NAME = "connect_prefs"
    const val DB_NAME = "connect_db"
}

val servicesModule = module {
    single {
        Moshi.Builder().add(MoshiDateTimeConverter()).add(LENIENT_FACTORY).build()
    }
    single { androidApplication().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE) }
    single {
        OkHttpClient.Builder().addInterceptor(get()).build()
    }
    single { FirebaseInstanceId.getInstance() }
    single {
        OkHttpClient.Builder().build().newWebSocketFactory(BuildConfig.SOCKET_URL)
    }
    single { SharedTokenStore(get()) } binds arrayOf(TokenStore::class, TokenProvider::class)
    single<Interceptor> { AuthInterceptor(get()) }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single {
        Room.databaseBuilder(
            androidApplication(),
            ConnectDatabase::class.java, DB_NAME
        ).build()
    }
    single { RxLocation(androidApplication()) }
    single {
        Scarlet.Builder()
            .webSocketFactory(get())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(androidApplication()))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
    }
}