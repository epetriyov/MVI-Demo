package com.connect.android.client.modules.settings

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable

class SettingsView(context: Context, initialState: SettingsVS) :
    BaseMviView<SettingsVIA, SettingsVOA, SettingsVS>(context, initialState) {
    override fun layoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewModel(): BaseMviViewModel<SettingsVIA, SettingsVS>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedViewState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputActions(): List<Observable<out SettingsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun outputActions(): List<Observable<out SettingsVOA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindState(viewState: SettingsVS) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}