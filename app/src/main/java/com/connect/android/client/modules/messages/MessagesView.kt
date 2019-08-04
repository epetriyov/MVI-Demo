package com.connect.android.client.modules.messages

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable

class MessagesView(context: Context, initialState: MessagesVS) :
    BaseMviView<MessagesVIA, MessagesVOA, MessagesVS>(context, initialState) {
    override fun layoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewModel(): BaseMviViewModel<MessagesVIA, MessagesVS>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedViewState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputActions(): List<Observable<out MessagesVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun outputActions(): List<Observable<out MessagesVOA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindState(viewState: MessagesVS) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}