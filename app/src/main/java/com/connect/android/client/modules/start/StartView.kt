package com.connect.android.client.modules.start

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.connect.android.client.R
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import io.reactivex.Observable
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class StartView(context: Context, initialState: StartVS) :
    BaseMviView<StartVIA, StartVOA, StartVS>(context, initialState) {

    val startViewModel: StartViewModel by inject { parametersOf(initialState) }

    override fun layoutId() = R.layout.view_start

    override fun viewModel(): BaseMviViewModel<StartVIA, StartVS>? = startViewModel

    override fun initView(savedViewState: Bundle?) {
    }

    override fun loadAction() = StartVIA.Init

    override fun inputActions(): List<Observable<out StartVIA>> = emptyList()

    override fun outputActions(): List<Observable<out StartVOA>> = emptyList()

    override fun bindState(viewState: StartVS) {
        viewState.isAuthorized.bind {
            outcomingAction(if (it) StartVOA.MainAction else StartVOA.LoginAction)
        }
    }

}