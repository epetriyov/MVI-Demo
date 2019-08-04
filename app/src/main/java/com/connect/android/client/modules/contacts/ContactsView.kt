package com.connect.android.client.modules.contacts

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable

class ContactsView(context: Context, initialState: ContactsVS) :
    BaseMviView<ContactsVIA, ContactsVOA, ContactsVS>(context, initialState) {
    override fun layoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewModel(): BaseMviViewModel<ContactsVIA, ContactsVS>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedViewState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputActions(): List<Observable<out ContactsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun outputActions(): List<Observable<out ContactsVOA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindState(viewState: ContactsVS) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}