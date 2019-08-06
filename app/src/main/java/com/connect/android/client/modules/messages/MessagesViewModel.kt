package com.connect.android.client.modules.messages

import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect
import kotlin.reflect.KClass

class MessagesViewModel(private val chatsRepository: ChatsRepository, initialState: MessagesVS) :
    BaseMviViewModel<MessagesVIA, MessagesVS>(initialState) {
    override fun filterActions(): List<KClass<out MessagesVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reducer(state: MessagesVS, action: MessagesVIA): MessagesVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<MessagesVS, MessagesVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}