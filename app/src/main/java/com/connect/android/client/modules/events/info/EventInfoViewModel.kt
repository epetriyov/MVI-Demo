package com.connect.android.client.modules.events.info

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.events.EventsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias EventInfoSideEffect = SideEffect<EventInfoVS, EventInfoVIA>

class EventInfoViewModel(private val eventsRepository: EventsRepository, initialState: EventInfoVS) :
    BaseMviViewModel<EventInfoVIA, EventInfoVS>(initialState) {

    private val acceptEvent: EventInfoSideEffect = { actions, viewState ->
        actions.ofType<EventInfoVIA.EventAction>()
            .observeOn(Schedulers.io())
            .flatMap {
            (if (viewState().event.peekContent()!!.accept) {
                eventsRepository.declineEvent(viewState().event.peekContent()!!.id)
            } else {
                eventsRepository.approveEvent(viewState().event.peekContent()!!.id)
            })
                .andThen(Observable.fromCallable { Unit })
                .map { EventInfoVIA.ActionDone as EventInfoVIA }
                .onLoggableError { t -> EventInfoVIA.Error(t.safeMessage()) }
                .startWith(EventInfoVIA.ActionProgress)
        }
    }

    override fun filterActions() = listOf(
        EventInfoVIA.Init::class,
        EventInfoVIA.Participants::class,
        EventInfoVIA.ActionDone::class,
        EventInfoVIA.ActionProgress::class,
        EventInfoVIA.Error::class
    )

    override fun reducer(state: EventInfoVS, action: EventInfoVIA): EventInfoVS {
        return when (action) {
            EventInfoVIA.Participants -> state.copy(participantsAction = ESO.withUpdate())
            EventInfoVIA.ActionDone -> state.copy(
                progress = false, event = state.event.peekContent()!!
                    .copy(accept = !state.event.peekContent()!!.accept).withUpdate()
            )
            EventInfoVIA.ActionProgress -> state.copy(progress = true)
            is EventInfoVIA.Error -> state.copy(progress = false, error = action.error.withUpdate())
            else -> state
        }
    }

    override fun sideEffects() = listOf(acceptEvent)
}