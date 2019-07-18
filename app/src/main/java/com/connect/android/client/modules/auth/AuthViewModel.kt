package com.connect.android.client.modules.auth

import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect

class AuthViewModel(private val authRepository: AuthRepository, initialState: AuthVS) :
    BaseMviViewModel<AuthVIA, AuthVS>(initialState) {
    override fun reducer(state: AuthVS, action: AuthVIA): AuthVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<AuthVS, AuthVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}