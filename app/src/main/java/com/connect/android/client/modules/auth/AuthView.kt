package com.connect.android.client.modules.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_login.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class AuthView(context: Context, initialState: AuthVS) :
    BaseMviView<AuthVIA, AuthVOA, AuthVS>(context, initialState) {

    val authViewModel: AuthViewModel by inject { parametersOf(initialState, context) }

    override fun layoutId() = R.layout.view_login

    override fun viewModel(): BaseMviViewModel<AuthVIA, AuthVS>? = authViewModel

    override fun initView(savedViewState: Bundle?) {
    }

    override fun inputActions(): List<Observable<out AuthVIA>> = listOf(
        btn_fb.clicks().map { AuthVIA.AuthFbClicked },
        btn_vk.clicks().map { AuthVIA.AuthVkClicked }
    )

    override fun loadAction() = AuthVIA.Init

    override fun outputActions(): List<Observable<out AuthVOA>> = emptyList()

    override fun bindState(viewState: AuthVS) {
        progress.isVisible = viewState.progress
        viewState.error.bind {
            showSnackbar(it)
        }
        viewState.success.bind {
            outcomingAction(AuthVOA.AuthSuccess)
        }
    }

    override fun onActivityResultAction(requestCode: Int, resultCode: Int, data: Intent?) =
        AuthVIA.OnActivityResult(requestCode, resultCode, data)
}