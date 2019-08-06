package com.connect.android.client.modules.profile

import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class ProfileVIA : ViewInputAction() {
    object Init : ProfileVIA()
    object Chat : ProfileVIA()
    object ChatCreateProgress : ProfileVIA()
    data class ChatCreateError(val errorMessage: String) : ProfileVIA()
    data class ChatCreated(val chat: com.connect.android.client.model.chats.Chat) : ProfileVIA()
}

sealed class ProfileVOA : ViewOutputAction() {
    object BackAction : ProfileVOA()
    data class ChatAction(val chat: com.connect.android.client.model.chats.Chat) : ProfileVOA()
}

@Parcelize
data class ProfileVS(
    val user: ORField<User>,
    val chat: ORField<com.connect.android.client.model.chats.Chat> = ORField.empty(),
    val error: ORField<String> = ORField.empty(),
    val chatCreateProgress: Boolean = false
) : ViewState()