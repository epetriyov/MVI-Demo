package com.connect.android.client.modules.events

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.jakewharton.rxbinding2.support.design.widget.selections
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_events.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

@SuppressLint("ViewConstructor")
class EventsView(context: Context, initialState: EventsVS) :
    BaseMviView<EventsVIA, EventsVOA, EventsVS>(context, initialState) {

    companion object {
        private const val SEARCH_THROTTLE_DELAY = 3L
    }

    val eventsViewModel: EventsViewModel by inject { parametersOf(initialState) }

    val eventsAdapter: EventsAdapter by inject { parametersOf(context) }

    val eventsLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    override fun layoutId() = R.layout.view_events

    override fun viewModel() = eventsViewModel

    override fun initView(savedViewState: Bundle?) {
        recycler_events.layoutManager = eventsLayoutManager
        recycler_events.adapter = eventsAdapter
    }

    override fun loadAction() = EventsVIA.Init

    override fun inputActions() =
        listOf(
            tabs.selections().map { EventsVIA.LoadRequest(edit_search.text.toString(), it.position) },
            edit_search.textChanges().throttleLast(SEARCH_THROTTLE_DELAY, TimeUnit.SECONDS)
                .map { EventsVIA.LoadRequest(it.toString(), tabs.selectedTabPosition) }
        )

    override fun outputActions(): List<Observable<out EventsVOA>> = listOf(
        eventsAdapter.itemSelections().map { EventsVOA.EventInfo(it) },
        eventsAdapter.eventParticipants().map { EventsVOA.EventMembers(it.id) }
    )

    override fun bindState(viewState: EventsVS) {
        with(viewState)
        {
            events.bind {
                eventsAdapter.submitList(it)
            }
            progressBar.isVisible = progress
            error.bind {
                showSnackbar(it)
            }
        }
    }
}