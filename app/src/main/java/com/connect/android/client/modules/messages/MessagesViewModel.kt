package com.connect.android.client.modules.messages

import com.connect.android.client.model.messages.MessagesRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect

class MessagesViewModel(private val messagesRepository: MessagesRepository, initialState: MessagesVS) :
    BaseMviViewModel<MessagesVIA, MessagesVS>(initialState) {
    override fun reducer(state: MessagesVS, action: MessagesVIA): MessagesVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<MessagesVS, MessagesVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}