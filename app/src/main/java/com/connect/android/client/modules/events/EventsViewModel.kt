package com.connect.android.client.modules.events

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.events.EventsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias EventsSideEffect = SideEffect<EventsVS, EventsVIA>

class EventsViewModel(private val eventsRepository: EventsRepository, initialState: EventsVS) :
    BaseMviViewModel<EventsVIA, EventsVS>(initialState) {

    private val loadEvents: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.LoadRequest>()
            .switchMap {
                eventsRepository.loadEvents(
                    if (!it.query.isNullOrEmpty()) it.query else null,
                    if (it.selectedTabPosition == 0) null else true
                )
                    .toObservable()
                    .map { EventsVIA.EventsLoaded(it) }
            }
    }

    private val refreshEvents: EventsSideEffect = { actions, _ ->
        actions.ofType<EventsVIA.RefreshEvents>()
            .observeOn(Schedulers.io())
            .flatMap {
                eventsRepository.updateEvents()
                    .andThen(Observable.fromCallable { Unit })
                    .map { EventsVIA.EventsUpdated as EventsVIA }
                    .onLoggableError { t -> EventsVIA.Error(t.safeMessage()) }
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
        actions.ofType<EventsVIA.EventAction>()
            .observeOn(Schedulers.io())
            .flatMap {
            (if (it.event.accept) {
                eventsRepository.declineEvent(it.event.id)
            } else {
                eventsRepository.approveEvent(it.event.id)
            })
                .andThen(Observable.fromCallable { Unit })
                .map { EventsVIA.RefreshEvents as EventsVIA }
                .onLoggableError { t -> EventsVIA.Error(t.safeMessage()) }
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