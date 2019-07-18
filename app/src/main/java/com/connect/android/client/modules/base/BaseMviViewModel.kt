package com.connect.android.client.modules.base

import androidx.lifecycle.ViewModel
import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.reduxStore
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.KClass

abstract class BaseMviViewModel<VIA : ViewInputAction, VS : ViewState>(private val initialState: VS) : ViewModel() {

    private val _state = BehaviorSubject.create<VS>()

    private var restoreState: VS? = null

    val state: VS
        get() = _state.value ?: restoreState ?: initialState

    val uiActionComposer: ObservableTransformer<VIA, VS> =
            ObservableTransformer { action ->
                var lastAction: VIA? = null
                action
                        .observeOn(Schedulers.io())
                        .reduxStore(
                                initialState = state,
                                sideEffects = sideEffects(),
                                reducer = { vs, via ->
                                    lastAction = via
                                    reducer(vs, via)
                                })
                        .doOnNext { _state.onNext(it) }
                        .filter { lastAction == null || !filterActions().contains(lastAction!!::class) }
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
            }

    open fun filterActions(): List<KClass<out VIA>> = emptyList()

    protected abstract fun reducer(state: VS, action: VIA): VS

    protected abstract fun sideEffects(): List<SideEffect<VS, VIA>>

    fun setRestoredState(vs: VS) {
        this.restoreState = vs
    }

    open fun stateToSave(): VS = state
}

class DefaultViewModel<VIA : ViewInputAction, VS : ViewState>(initialState: VS) : BaseMviViewModel<VIA, VS>(initialState) {
    override fun reducer(state: VS, action: VIA): VS {
        return state
    }

    override fun sideEffects(): List<SideEffect<VS, VIA>> {
        return emptyList()
    }
}