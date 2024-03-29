package com.connect.android.client.modules.main

import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.base.withoutUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias MainSideEffect = SideEffect<MainVS, MainVIA>

class MainViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    initialState: MainVS
) : BaseMviViewModel<MainVIA, MainVS>(initialState) {

    private val init: MainSideEffect = { actions, viewState ->
        actions.ofType<MainVIA.Init>().publish {
            Observable.merge(
                it
                    .observeOn(Schedulers.io())
                    .flatMap {
                    authRepository.updateNotificationToken()
                        .onErrorComplete().andThen(Observable.fromCallable { MainVIA.TokenUpdated })
                },
                it.filter { viewState().selectedTabId.isEmpty() }
                    .map { MainVIA.FirstTabSelect },
                it.filter { !viewState().selectedTabId.isEmpty() }
                    .map { MainVIA.TabSelect(viewState().selectedTabId.peekContent()!!) })
        }
    }

    override fun filterActions() =
        listOf(MainVIA.TabClicked::class, MainVIA.FirstTabSelect::class, MainVIA.TabSelect::class)

    override fun reducer(state: MainVS, action: MainVIA): MainVS {
        return when (action) {
            is MainVIA.TabClicked -> state.copy(
                selectedTabId = action.tabId.withoutUpdate(),
                navigateToTab = ESO.withUpdate()
            )
            is MainVIA.TabSelect -> state.copy(selectedTabId = action.tabId.withUpdate())
            MainVIA.FirstTabSelect -> state.copy(selectFirstTab = ESO.withUpdate())
            else -> state
        }
    }

    override fun sideEffects() = listOf(init)
}