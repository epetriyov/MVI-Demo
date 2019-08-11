package com.connect.android.client.modules.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.connect.android.client.extensions.getWithClassNameKey
import com.connect.android.client.extensions.putWithClassNameKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

abstract class BaseMviView<VIA : ViewInputAction, VEA : ViewOutputAction, VS : ViewState>(
    context: Context, private val viewState: VS
) : FrameLayout(context), KoinComponent, ActivityResultListener {

    val layoutInflater: LayoutInflater by inject { parametersOf(context) }

    private val outputAction = PublishSubject.create<VEA>()

    fun init(savedViewState: Bundle? = null) {
        layoutInflater.inflate(layoutId(), this)
        initView(savedViewState)
    }

    private val compositeDisposable = CompositeDisposable()

    private val incomingActions = BehaviorSubject.create<VIA>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (context as ActivityResultObservable).addOnActivityResultListener(this)
        subscribeOnActions()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        (context as ActivityResultObservable).removeOnActivityResultListener(this)
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onActivityResultAction(requestCode, resultCode, data)?.let { incomingAction(it) }
    }

    fun onRestoreViewState(savedViewState: Bundle) {
        savedViewState.getWithClassNameKey<ViewState>()?.let {
            viewModel()?.setRestoredState(it as VS)
        }
    }

    fun incomingAction(incomingAction: VIA) {
        incomingActions.onNext(incomingAction)
    }

    open fun handleBack() = false

    open fun onSaveInstanceState(outState: Bundle) {
        viewModel()?.stateToSave()?.let { outState.putWithClassNameKey<ViewState>(it) }
    }

    open fun getChildContainer(): ViewGroup? = null

    open fun onDestroyView() {}

    open fun onAttach() {}

    open fun onDetach() {}

    fun actions(): Observable<out VEA> = Observable.merge(outputActions() + outputAction).publish().autoConnect()

    protected fun outcomingAction(outcomingAction: VEA) {
        outputAction.onNext(outcomingAction)
    }

    protected open fun loadAction(): VIA? = null

    protected open fun onActivityResultAction(requestCode: Int, resultCode: Int, data: Intent?): VIA? = null

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun viewModel(): BaseMviViewModel<VIA, VS>?

    protected abstract fun initView(savedViewState: Bundle? = null)

    protected abstract fun inputActions(): List<Observable<out VIA>>

    protected abstract fun outputActions(): List<Observable<out VEA>>

    protected abstract fun bindState(viewState: VS)

    private fun subscribeOnActions() {
        viewModel()?.let {
            Observable.merge(inputActions() + incomingActions +
                    (loadAction()?.let { Observable.just(it) } ?: Observable.never<VIA>()))
                .compose(it.uiActionComposer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::bindState)
                .addTo(compositeDisposable)
        }
    }
}