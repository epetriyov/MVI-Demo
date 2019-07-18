package com.connect.android.client.modules.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.connect.android.client.extensions.getWithClassNameKey
import com.connect.android.client.extensions.putWithClassNameKey
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

abstract class FragmentMviView<VIA : ViewInputAction, VEA : ViewOutputAction, VS : ViewState>(private val viewState: VS) :
    Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private val incomingActions = BehaviorSubject.create<VIA>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel()?.stateToSave()?.let { outState.putWithClassNameKey<ViewState>(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependenciesGraph(viewState)
        savedInstanceState?.getWithClassNameKey<ViewState>()?.let {
            viewModel()?.setRestoredState(it as VS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        subscribeOnActions()
    }

    fun incomingAction(incomingAction: VIA) {
        incomingActions.onNext(incomingAction)
    }

    fun actions(): Observable<out VEA> = Observable.merge(outputActions()).publish().autoConnect()

    protected open fun loadAction(): VIA? = null

    protected abstract fun layoutId(): Int

    protected abstract fun viewModel(): BaseMviViewModel<VIA, VS>?

    protected abstract fun initDependenciesGraph(initialState: VS)

    protected abstract fun initView(savedViewState: Bundle? = null)

    protected abstract fun inputActions(): List<Observable<out VIA>>

    protected abstract fun outputActions(): List<Observable<out VEA>>

    protected abstract fun bindState(viewState: VS)

    private fun subscribeOnActions() {
        viewModel()?.let {
            Observable.merge(inputActions() + incomingActions +
                    (loadAction()?.let { Observable.just(it) } ?: Observable.never<VIA>()))
                .doOnNext { Log.d("DEBUG!!!", "Pre composer: " + it.toString()) }
                .compose(it.uiActionComposer)
                .subscribe(::bindState)
                .addTo(compositeDisposable)
        }
    }

    private fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}