package com.connect.android.client.modules.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.bluelinelabs.conductor.Router
import com.connect.android.client.extensions.setRoot

abstract class BaseMviController<T : BaseMviView<VIA, VEA, *>, VIA : ViewInputAction,
        VEA : ViewOutputAction>(bundle: Bundle? = null) : RestoreViewOnCreateController(bundle) {

    private var pendingEvent: VIA? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): T {
        return buildView(container.context).apply { init(savedViewState) }
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        view()?.onDestroyView()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        view()?.onDetach()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        setupChildRouter()
        (view as T).actions().subscribe(::handleViewEvents)
        view()?.onAttach()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        (view as T).onSaveInstanceState(outState)
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        (view as T).onRestoreViewState(savedViewState)
        pendingEvent?.let {
            internalSentEventToView(it)
            pendingEvent = null
        }
    }

    override fun handleBack(): Boolean {
        val childRouterHandleBack = getChildRouter()?.let {
            if (it.backstack.size > 1) it.handleBack() else null
        }
        return childRouterHandleBack ?: view()!!.handleBack()
    }

    protected fun sentEventToView(event: VIA) {
        if (view() == null) {
            pendingEvent = event
        } else {
            internalSentEventToView(event)
        }
    }

    fun getChildRouter(): Router? {
        return childRouters.firstOrNull() ?: view()?.getChildContainer()?.let { getChildRouter(it) }
    }

    open fun setupChildRouter() {
        getChildRouter()?.setRoot(rootChildController(), childControllerTag())
    }

    fun view(): T? {
        return view as? T
    }

    protected fun getChildController(): Controller? = getChildRouter()?.getControllerWithTag(childControllerTag())

    protected open fun rootChildController(): Controller = RootController()

    protected open fun childControllerTag(): String = ""

    abstract fun buildView(context: Context): T

    abstract fun handleViewEvents(action: VEA)

    private fun internalSentEventToView(event: VIA) {
        view()!!.incomingAction(event)
    }
}

class RootController(bundle: Bundle? = null) : Controller(bundle) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return View(container.context)
    }
}