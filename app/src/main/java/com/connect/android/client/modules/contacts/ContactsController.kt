package com.connect.android.client.modules.contacts

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviController

class ContactsController(bundle: Bundle? = null): BaseMviController<ContactsView,ContactsVIA,ContactsVOA>(bundle) {
    override fun buildView(context: Context): ContactsView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleViewEvents(action: ContactsVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}