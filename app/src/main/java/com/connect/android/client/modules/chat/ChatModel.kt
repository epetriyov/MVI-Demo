package com.connect.android.client.modules.chat

import com.connect.android.client.model.chats.Chat
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.*
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.io.Serializable

sealed class ChatVIA : ViewInputAction() {
    object Init : ChatVIA()
    object ProfileClickedAction : ChatVIA()
    object MessageSend : ChatVIA()
    object SendProgress : ChatVIA()
    object ObserveStarted : ChatVIA()
    object LoadNext : ChatVIA()
    object NextLoaded : ChatVIA()
    object NextLoadProgress : ChatVIA()
    data class TextChanged(val text: String) : ChatVIA()
    data class NextLoadError(val error: String) : ChatVIA()
    data class SendError(val error: String) : ChatVIA()
    data class SendAction(val message: String) : ChatVIA()
    data class MessagesLoaded(val messages: List<DisplayableMessage>) : ChatVIA()
}

sealed class ChatVOA : ViewOutputAction() {
    object BackAction : ChatVOA()
    data class ProfileAction(val user: User) : ChatVOA()
}

@Parcelize
data class ChatVS(
    val chat: ORField<Chat>,
    val messages: ORField<List<DisplayableMessage>> = ORField.empty(),
    val sendError: ORField<String> = ORField.empty(),
    val nextLoadError: ORField<String> = ORField.empty(),
    val isSending: Boolean = false,
    val messageSent: ORField<ESO> = ORField.empty(),
    val isLoadingNext: Boolean = false,
    val buttonEnabled: Boolean = false,
    val profileCLicked: ORField<ESO> = ORField.empty()
) :
    ViewState()

data class DisplayableMessage(val id: String, val text: String, val time: DateTime, val isMine: Boolean): Serializable