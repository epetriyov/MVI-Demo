package com.connect.android.client.modules.start

import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias StartSideEffect = SideEffect<StartVS, StartVIA>

class StartViewModel(private val authRepository: AuthRepository, initialState: StartVS) :
    BaseMviViewModel<StartVIA, StartVS>(initialState) {

    private val init: StartSideEffect = { actions, _ ->
        actions.ofType<StartVIA.Init>()
            .observeOn(Schedulers.io())
            .map { authRepository.isAuthorized() }
            .map { StartVIA.Route(it) }
    }

    override fun reducer(state: StartVS, action: StartVIA): StartVS {
        return when (action) {
            is StartVIA.Route -> state.copy(isAuthorized = action.isAuthorized.withUpdate())
            else -> state
        }
    }

    override fun sideEffects(): List<StartSideEffect> = listOf(init)

    override fun filterActions() = listOf(StartVIA.Route::class)
}