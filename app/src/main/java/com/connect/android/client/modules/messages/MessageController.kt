package com.connect.android.client.modules.messages

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.chat.ChatController

class MessageController(bundle: Bundle? = null) : BaseMviController<MessagesView, MessagesVIA, MessagesVOA>(bundle) {
    override fun buildView(context: Context) = MessagesView(context, MessagesVS())

    override fun handleViewEvents(action: MessagesVOA) {
        Do exhaustive when (action) {
            is MessagesVOA.MessageSelect -> router.pushController(ChatController.newInstance(action.chat).buildRouterTransaction())
        }
    }
}