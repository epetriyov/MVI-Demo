package com.connect.android.client.modules.auth

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias AuthSideEffect = SideEffect<AuthVS, AuthVIA>

class AuthViewModel(
    private val authRepository: AuthRepository, private val socialHelper: SocialHelper,
    initialState: AuthVS
) :
    BaseMviViewModel<AuthVIA, AuthVS>(initialState) {

    private val fbClick: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.AuthFbClicked>().doOnNext { socialHelper.loginViaFb() }
            .map { AuthVIA.AuthFbSdk }
    }

    private val vkClick: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.AuthVkClicked>().doOnNext { socialHelper.loginViaVk() }
            .map { AuthVIA.AuthVkSdk }
    }

    private val onActivityResult: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.OnActivityResult>()
            .doOnNext { socialHelper.onActivityResult(it.requestCode, it.resultCode, it.data) }
            .map { AuthVIA.SocialResult }
    }

    private val init: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.Init>()
            .flatMap {
                socialHelper.loginResult()
            }
            .map {
                with(it)
                {
                    when {
                        error != null -> AuthVIA.Error(error)
                        token != null -> when (socialType) {
                            SocialType.VK -> AuthVIA.ProceedVk(token)
                            SocialType.FB -> AuthVIA.ProceedFb(token)
                        }
                        else -> throw IllegalStateException("social error and token are nulls")
                    }
                }
            }
    }

    private val fbAuth: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.ProceedFb>()
            .observeOn(Schedulers.io())
            .switchMap {
            authRepository.authFb(it.token)
                .andThen(Observable.fromCallable { Unit })
                .map { AuthVIA.Success as AuthVIA }
                .onLoggableError { t -> AuthVIA.Error(t.safeMessage()) }
                .startWith(AuthVIA.Progress)
        }
    }

    private val vkAuth: AuthSideEffect = { actions, _ ->
        actions.ofType<AuthVIA.ProceedVk>()
            .observeOn(Schedulers.io())
            .switchMap {
            authRepository.authVk(it.token)
                .andThen(Observable.fromCallable { Unit })
                .map { AuthVIA.Success as AuthVIA }
                .onLoggableError { t -> AuthVIA.Error(t.safeMessage()) }
                .startWith(AuthVIA.Progress)
        }
    }

    override fun reducer(state: AuthVS, action: AuthVIA): AuthVS {
        return when (action) {
            is AuthVIA.Error -> state.copy(error = action.error.withUpdate(), progress = false)
            is AuthVIA.Success -> state.copy(progress = false, success = ESO.withUpdate())
            AuthVIA.Progress -> state.copy(progress = true)
            else -> state
        }
    }

    override fun sideEffects(): List<AuthSideEffect> = listOf(init, fbClick, vkClick, fbAuth, vkAuth, onActivityResult)

    override fun filterActions() = listOf(AuthVIA.Progress::class, AuthVIA.Success::class, AuthVIA.Error::class)
}