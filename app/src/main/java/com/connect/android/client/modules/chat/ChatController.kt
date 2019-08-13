package com.connect.android.client.modules.chat

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.model.chats.Chat
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.profile.ProfileController

class ChatController(bundle: Bundle? = null) : BaseMviController<ChatView, ChatVIA, ChatVOA>(bundle) {

    companion object {
        private const val EXTRA_CHAT = "extra_chat"

        fun newInstance(chat: Chat): ChatController {
            return ChatController(
                BundleBuilder(Bundle())
                    .putSerializable(EXTRA_CHAT, chat)
                    .build()
            )
        }
    }

    override fun buildView(context: Context): ChatView =
        ChatView(context, ChatVS((args.getSerializable(EXTRA_CHAT) as Chat).withUpdate()))

    override fun handleViewEvents(action: ChatVOA) {
        Do exhaustive when (action) {
            is ChatVOA.ProfileAction ->
                router.pushController(ProfileController.newInstance(action.user).buildRouterTransaction())
            ChatVOA.BackAction -> router.handleBack()
        }
    }
}