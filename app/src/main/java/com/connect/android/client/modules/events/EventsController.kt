package com.connect.android.client.modules.events

import android.content.Context
import android.os.Bundle
import com.connect.android.client.modules.base.BaseMviController

class EventsController(bundle: Bundle? = null): BaseMviController<EventsView,EventsVIA,EventsVOA>(bundle) {
    override fun buildView(context: Context): EventsView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleViewEvents(action: EventsVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}