package com.connect.android.client.modules

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.modules.auth.AuthVS
import com.connect.android.client.modules.auth.AuthViewModel
import com.connect.android.client.modules.auth.SocialHelper
import com.connect.android.client.modules.auth.SocialHelperImpl
import com.connect.android.client.modules.chat.ChatVS
import com.connect.android.client.modules.chat.ChatViewModel
import com.connect.android.client.modules.chat.MessagesAdapter
import com.connect.android.client.modules.contacts.ContactsAdapter
import com.connect.android.client.modules.contacts.ContactsVS
import com.connect.android.client.modules.contacts.ContactsViewModel
import com.connect.android.client.modules.events.EventsAdapter
import com.connect.android.client.modules.events.EventsVS
import com.connect.android.client.modules.events.EventsViewModel
import com.connect.android.client.modules.events.info.EventInfoVS
import com.connect.android.client.modules.events.info.EventInfoViewModel
import com.connect.android.client.modules.main.MainVS
import com.connect.android.client.modules.main.MainViewModel
import com.connect.android.client.modules.messages.ChatsAdapter
import com.connect.android.client.modules.messages.MessagesVS
import com.connect.android.client.modules.messages.MessagesViewModel
import com.connect.android.client.modules.myprofile.MyProfileItemAdapter
import com.connect.android.client.modules.myprofile.MyProfileVS
import com.connect.android.client.modules.myprofile.MyProfileViewModel
import com.connect.android.client.modules.myprofile.edit.ProfileEditVS
import com.connect.android.client.modules.myprofile.edit.ProfileEditViewModel
import com.connect.android.client.modules.myprofile.form.ProfileFormVS
import com.connect.android.client.modules.myprofile.form.ProfileFormViewModel
import com.connect.android.client.modules.profile.ProfileItemAdapter
import com.connect.android.client.modules.profile.ProfileVS
import com.connect.android.client.modules.profile.ProfileViewModel
import com.connect.android.client.modules.recommendations.*
import com.connect.android.client.modules.settings.SettingsVS
import com.connect.android.client.modules.settings.SettingsViewModel
import com.connect.android.client.modules.start.StartVS
import com.connect.android.client.modules.start.StartViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
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
    factory { (initialState: ChatVS) -> ChatViewModel(get(), get(), get(), initialState) }
    factory { (initialState: ContactsVS) -> ContactsViewModel(get(), get(), get(), initialState) }
    factory { (initialState: MainVS) -> MainViewModel(get(), get(), initialState) }
    factory { (initialState: MessagesVS) -> MessagesViewModel(get(), initialState) }
    factory { (initialState: MyProfileVS) -> MyProfileViewModel(get(), get(), get(), get(), initialState) }
    factory { (initialState: ProfileFormVS) -> ProfileFormViewModel(get(), get(), get(), initialState) }
    factory { (initialState: ProfileEditVS) -> ProfileEditViewModel(get(), initialState) }
    factory { (initialState: ProfileVS) -> ProfileViewModel(get(), get(), initialState) }
    factory { (initialState: SettingsVS) -> SettingsViewModel(get(), get(), initialState) }
    factory { (initialState: StartVS) -> StartViewModel(get(), initialState) }
    factory { (initialState: EventsVS) -> EventsViewModel(get(), initialState) }
    factory { (initialState: EventInfoVS) -> EventInfoViewModel(get(), initialState) }
}

val viewModule = module {
    factory { (context: Context) -> LayoutInflater.from(context) }
    factory { (context: FragmentActivity) -> RxPermissions(context) }
    factory<SocialHelper> { (context: Activity) -> SocialHelperImpl(context) }
    factory { (context: Context) -> LinearLayoutManager(context) }
}

val recommendationsView = module {
    factory<CardStackListener> { RecommendationsDelegate() }
    factory { (context: Context) -> CardStackLayoutManager(context, get()) }
    factory { (context: Context) -> RecommendationsAdapter(get(parameters = { parametersOf(context) })) }
    factory { (context: Context) ->
        RecommendationsHelper(
            get(parameters = { parametersOf(context) }),
            get(parameters = { parametersOf(context) })
        )
    }
}

val messagesView = module {
    factory { (context: Context) -> MessagesAdapter(get(parameters = { parametersOf(context) })) }
    factory(named("reversed")) { (context: Context) ->
        LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
            reverseLayout = true
            stackFromEnd = true
        }
    }
}

val profileView = module {
    factory { (context: Context) ->
        ProfileItemAdapter(
            get(parameters = { parametersOf(context) })
        )
    }
}

val myProfileView = module {
    factory { (context: Context) ->
        MyProfileItemAdapter(
            get(parameters = { parametersOf(context) })
        )
    }
}

val eventsView = module {
    factory { (context: Context) ->
        EventsAdapter(
            get(parameters = { parametersOf(context) })
        )
    }
}

val contactsView = module {
    factory { (context: Context) ->
        ContactsAdapter(
            get(parameters = { parametersOf(context) })
        )
    }
}

val chatsView = module {
    factory { (context: Context) ->
        ChatsAdapter(
            get(parameters = { parametersOf(context) })
        )
    }
}