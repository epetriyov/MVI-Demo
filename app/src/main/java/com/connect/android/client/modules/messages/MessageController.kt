package com.connect.android.client.modules.messages

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviController

class MessageController(bundle: Bundle? = null): BaseMviController<MessagesView,MessagesVIA,MessagesVOA>(bundle) {
    override fun buildView(context: Context): MessagesView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleViewEvents(action: MessagesVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}