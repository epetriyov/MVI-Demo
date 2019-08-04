package com.connect.android.client.modules.events

import com.connect.android.client.model.events.EventsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect
import kotlin.reflect.KClass

class EventsViewModel(private val eventsRepository: EventsRepository, initialState: EventsVS) :
    BaseMviViewModel<EventsVIA, EventsVS>(initialState) {
    override fun filterActions(): List<KClass<out EventsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reducer(state: EventsVS, action: EventsVIA): EventsVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<EventsVS, EventsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}