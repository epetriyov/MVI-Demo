package com.connect.android.client.modules.profile

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable

class ProfileView(context: Context, initialState: ProfileVS) :
    BaseMviView<ProfileVIA, ProfileVOA, ProfileVS>(context, initialState) {
    override fun layoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewModel(): BaseMviViewModel<ProfileVIA, ProfileVS>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedViewState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputActions(): List<Observable<out ProfileVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun outputActions(): List<Observable<out ProfileVOA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindState(viewState: ProfileVS) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}