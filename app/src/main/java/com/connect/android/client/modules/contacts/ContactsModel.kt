package com.connect.android.client.modules.contacts

import com.connect.android.client.model.chats.Chat
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class ContactsVIA : ViewInputAction() {
    object Init : ContactsVIA()
    object FetchUsers : ContactsVIA()
    object LoadProgress : ContactsVIA()
    object LoadSuccess : ContactsVIA()
    data class ChatCreated(val chat: Chat) : ContactsVIA()
    data class LoadError(val error: String) : ContactsVIA()
    data class LoadRequest(val query: String? = null) : ContactsVIA()
    data class ChatRequest(val user: User) : ContactsVIA()
    data class UsersLoaded(val users: List<User>) : ContactsVIA()
}

sealed class ContactsVOA : ViewOutputAction() {
    data class UserSelect(val user: User) : ContactsVOA()
    data class ChatCreate(val chat: Chat) : ContactsVOA()
}

@Parcelize
data class ContactsVS(
    val eventId: String? = null,
    val contacts: ORField<List<User>> = ORField.empty(),
    val progress: Boolean = false,
    val error: ORField<String> = ORField.empty(),
    val chatCreated: ORField<Chat> = ORField.empty()
) : ViewState()