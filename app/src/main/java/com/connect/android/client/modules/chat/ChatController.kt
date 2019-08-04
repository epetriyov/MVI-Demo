package com.connect.android.client.modules.chat

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.model.chats.Chat
import com.connect.android.client.modules.base.BaseMviController

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

    override fun buildView(context: Context): ChatView = ChatView(context, ChatVS())

    override fun handleViewEvents(action: ChatVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}