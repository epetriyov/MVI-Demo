package com.connect.android.client.modules.recommendations

import com.connect.android.client.model.chats.Chat
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class RecommendationsVIA : ViewInputAction() {
    object Init : RecommendationsVIA()
    object OpenLocationPermission : RecommendationsVIA()
    object SendLocation : RecommendationsVIA()
    object GetRecommendations : RecommendationsVIA()
    object Progress : RecommendationsVIA()
    object Connecting : RecommendationsVIA()
    object Disconnected : RecommendationsVIA()
    object StartChatConnected : RecommendationsVIA()
    data class ChatCreated(val chat: Chat) : RecommendationsVIA()
    data class StartChat(val user: User) : RecommendationsVIA()
    data class Connected(val user: User) : RecommendationsVIA()
    data class Recommendations(val recommendations: List<User>) : RecommendationsVIA()
    data class Error(val error: String) : RecommendationsVIA()
    data class UserConnect(val user: User) : RecommendationsVIA()
    data class UserDisconnect(val userId: String) : RecommendationsVIA()
}

sealed class RecommendationsVOA : ViewOutputAction() {
    data class OpenProfile(val user: User) : RecommendationsVOA()
    data class OpenChat(val chat: Chat) : RecommendationsVOA()
}

@Parcelize
data class RecommendationsVS(
    val progress: Boolean = false,
    val showLocationPermission: Boolean = false,
    val openChat: ORField<Chat> = ORField.empty(),
    val connected: ORField<User> = ORField.empty(),
    val recommendations: ORField<List<User>> = ORField.empty(),
    val error: ORField<String> = ORField.empty()
) : ViewState()