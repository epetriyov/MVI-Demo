package com.connect.android.client.modules.messages

import com.connect.android.client.model.chats.Chat
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class MessagesVIA : ViewInputAction() {
    object Init : MessagesVIA()
    object FetchData : MessagesVIA()
    object FetchProgress : MessagesVIA()
    object FetchSuccess : MessagesVIA()
    data class FetchError(val error: String) : MessagesVIA()
    data class LoadRequest(val query: String? = null) : MessagesVIA()
    data class ChatsLoaded(val chats: List<Chat>) : MessagesVIA()
}

sealed class MessagesVOA : ViewOutputAction() {
    data class MessageSelect(val chat: Chat) : MessagesVOA()
}

@Parcelize
data class MessagesVS(
    val chats: ORField<List<Chat>> = ORField.empty(),
    val error: ORField<String> = ORField.empty(),
    val progress: Boolean = false
) : ViewState()