package com.connect.android.client.modules

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.connect.android.client.modules.auth.AuthVS
import com.connect.android.client.modules.auth.AuthViewModel
import com.connect.android.client.modules.auth.SocialHelper
import com.connect.android.client.modules.auth.SocialHelperImpl
import com.connect.android.client.modules.chat.ChatVS
import com.connect.android.client.modules.chat.ChatViewModel
import com.connect.android.client.modules.contacts.ContactsVS
import com.connect.android.client.modules.contacts.ContactsViewModel
import com.connect.android.client.modules.events.EventsVS
import com.connect.android.client.modules.events.EventsViewModel
import com.connect.android.client.modules.main.MainVS
import com.connect.android.client.modules.main.MainViewModel
import com.connect.android.client.modules.messages.MessagesVS
import com.connect.android.client.modules.messages.MessagesViewModel
import com.connect.android.client.modules.myprofile.MyProfileVS
import com.connect.android.client.modules.myprofile.MyProfileViewModel
import com.connect.android.client.modules.profile.ProfileVS
import com.connect.android.client.modules.profile.ProfileViewModel
import com.connect.android.client.modules.recommendations.*
import com.connect.android.client.modules.settings.SettingsVS
import com.connect.android.client.modules.settings.SettingsViewModel
import com.connect.android.client.modules.start.StartVS
import com.connect.android.client.modules.start.StartViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelsModule = module {
    factory { (initialState: AuthVS, context: Activity) ->
        AuthViewModel(
            get(),
            get(parameters = { parametersOf(context) }),
            initialState
        )
    }
    factory { (context: FragmentActivity, initialState: RecommendationsVS) ->
        RecommendationsViewModel(
            get(),
            get(),
            get(),
            get(parameters = { parametersOf(context) }),
            initialState
        )
    }
    factory { (initialState: ChatVS) -> ChatViewModel(get(), get(), initialState) }
    factory { (initialState: ContactsVS) -> ContactsViewModel(get(), get(), initialState) }
    factory { (initialState: MainVS) -> MainViewModel(get(), get(), initialState) }
    factory { (initialState: MessagesVS) -> MessagesViewModel(get(), initialState) }
    factory { (initialState: MyProfileVS) -> MyProfileViewModel(get(), initialState) }
    factory { (initialState: ProfileVS) -> ProfileViewModel(get(), get(), initialState) }
    factory { (initialState: SettingsVS) -> SettingsViewModel(get(), get(), initialState) }
    factory { (initialState: StartVS) -> StartViewModel(get(), initialState) }
    factory { (initialState: EventsVS) -> EventsViewModel(get(), initialState) }
}

val viewModule = module {
    factory<SocialHelper> { (context: Activity) -> SocialHelperImpl(context) }
    single { (context: Context) -> LayoutInflater.from(context) }
    single { (context: FragmentActivity) -> RxPermissions(context) }
}

val recommendationsView = module {
    factory { RecommendationsDelegate() }
    factory { (context: Context) -> RecommendationsAdapter(get(parameters = { parametersOf(context) })) }
    factory { (context: Context) ->
        RecommendationsHelper(
            get(parameters = { parametersOf(context) }),
            get(parameters = { parametersOf(context) })
        )
    }
    factory { (context: Context) -> CardStackLayoutManager(context, get()) }
}