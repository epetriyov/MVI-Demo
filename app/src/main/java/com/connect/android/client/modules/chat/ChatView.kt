package com.connect.android.client.modules.chat

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable

class ChatView(context: Context, initialState: ChatVS) : BaseMviView<ChatVIA, ChatVOA, ChatVS>(context, initialState) {
    override fun layoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewModel(): BaseMviViewModel<ChatVIA, ChatVS>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedViewState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputActions(): List<Observable<out ChatVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun outputActions(): List<Observable<out ChatVOA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindState(viewState: ChatVS) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}