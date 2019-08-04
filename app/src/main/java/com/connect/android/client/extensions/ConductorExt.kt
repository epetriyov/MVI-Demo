package com.connect.android.client.extensions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

fun Controller.with(targetController: Controller): Controller {
    this.targetController = targetController
    return this
}

fun RouterTransaction.pushWith(router: Router){
    router.pushController(this)
}

fun RouterTransaction.pushWith(parentController: ParentController){
    parentController.pushTransaction(this)
}

fun Controller.buildRouterTransaction(tag: String? = null): RouterTransaction {
    return RouterTransaction.with(this).tag(tag)
}

fun Router.setRoot(controller: Controller, tag: String? = null) {
    if (!hasRootController()) {
        setRoot(controller.buildRouterTransaction(tag))
    }
}

interface ParentController {
    fun pushTransaction(transaction: RouterTransaction)
}