package com.connect.android.client.modules.chat

import com.connect.android.client.model.chat.ChatRepository
import com.connect.android.client.model.messages.MessagesRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect
import kotlin.reflect.KClass

class ChatViewModel(
    private val messagesRepository: MessagesRepository,
    private val chatRepository: ChatRepository,
    initialState: ChatVS
) : BaseMviViewModel<ChatVIA, ChatVS>(initialState) {
    override fun filterActions(): List<KClass<out ChatVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reducer(state: ChatVS, action: ChatVIA): ChatVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<ChatVS, ChatVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}