package com.connect.android.client.modules

import com.connect.android.client.modules.auth.AuthVS
import com.connect.android.client.modules.auth.AuthViewModel
import com.connect.android.client.modules.chat.ChatVS
import com.connect.android.client.modules.chat.ChatViewModel
import com.connect.android.client.modules.contacts.ContactsVS
import com.connect.android.client.modules.contacts.ContactsViewModel
import com.connect.android.client.modules.menu.MenuVS
import com.connect.android.client.modules.menu.MenuViewModel
import com.connect.android.client.modules.messages.MessagesVS
import com.connect.android.client.modules.messages.MessagesViewModel
import com.connect.android.client.modules.myprofile.MyProfileVS
import com.connect.android.client.modules.myprofile.MyProfileViewModel
import com.connect.android.client.modules.profile.ProfileVS
import com.connect.android.client.modules.profile.ProfileViewModel
import com.connect.android.client.modules.recommendations.RecommendationsVS
import com.connect.android.client.modules.recommendations.RecommendationsViewModel
import com.connect.android.client.modules.settings.SettingsVS
import com.connect.android.client.modules.settings.SettingsViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    factory { (initialState: AuthVS) -> AuthViewModel(get(), initialState) }
    factory { (initialState: ChatVS) -> ChatViewModel(get(), get(), initialState) }
    factory { (initialState: ContactsVS) -> ContactsViewModel(get(), get(), initialState) }
    factory { (initialState: MenuVS) -> MenuViewModel(get(), initialState) }
    factory { (initialState: MessagesVS) -> MessagesViewModel(get(), initialState) }
    factory { (initialState: MyProfileVS) -> MyProfileViewModel(get(), initialState) }
    factory { (initialState: ProfileVS) -> ProfileViewModel(get(), get(), initialState) }
    factory { (initialState: RecommendationsVS) -> RecommendationsViewModel(get(), get(), initialState) }
    factory { (initialState: SettingsVS) -> SettingsViewModel(get(), get(), initialState) }
}