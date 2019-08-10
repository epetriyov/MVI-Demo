package com.connect.android.client.modules.events

import android.text.TextUtils
import com.connect.android.client.model.events.EventsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

typealias EventsSideEffect = SideEffect<EventsVS, EventsVIA>

class EventsViewModel(private val eventsRepository: EventsRepository, initialState: EventsVS) :
    BaseMviViewModel<EventsVIA, EventsVS>(initialState) {

    private val loadEvents: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.LoadRequest>()
            .flatMap {
                eventsRepository.loadEvents(
                    if (!TextUtils.isEmpty(it.query)) it.query else null,
                    if (it.selectedTabPosition == 0) null else true
                )
                    .toObservable()
                    .map { EventsVIA.EventsLoaded(it) }
            }
    }

    private val refreshEvents: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.RefreshEvents>()
            .flatMap {
                eventsRepository.updateEvents()
                    .andThen(Observable.just(Unit))
                    .map { EventsVIA.EventsUpdated as EventsVIA }
                    .onErrorReturn { t -> EventsVIA.Error(t.localizedMessage) }
                    .startWith(EventsVIA.UpdateProgress)
            }
    }

    private val initialLoad: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.Init>().publish {
            Observable.merge(
                it.map { EventsVIA.RefreshEvents },
                it.map { EventsVIA.LoadRequest() }
            )
        }
    }

    private val acceptEvent: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.EventAction>().flatMap {
            (if (it.event.accept) {
                eventsRepository.declineEvent(it.event.id)
            } else {
                eventsRepository.approveEvent(it.event.id)
            })
                .andThen(Observable.just(Unit))
                .map { EventsVIA.RefreshEvents as EventsVIA }
                .onErrorReturn { t -> EventsVIA.Error(t.localizedMessage) }
                .startWith(EventsVIA.UpdateProgress)
        }
    }

    override fun filterActions() = listOf(
        EventsVIA.EventsLoaded::class,
        EventsVIA.Error::class,
        EventsVIA.UpdateProgress::class,
        EventsVIA.EventsUpdated::class
    )

    override fun reducer(state: EventsVS, action: EventsVIA): EventsVS {
        return when (action) {
            is EventsVIA.EventsLoaded -> state.copy(events = action.events.withUpdate())
            is EventsVIA.Error -> state.copy(progress = false, error = action.error.withUpdate())
            EventsVIA.UpdateProgress -> state.copy(progress = true)
            EventsVIA.EventsUpdated -> state.copy(progress = false)
            else -> state
        }
    }

    override fun sideEffects() = listOf(loadEvents, refreshEvents, acceptEvent, initialLoad)
}