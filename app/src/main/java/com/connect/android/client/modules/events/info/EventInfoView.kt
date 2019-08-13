package com.connect.android.client.modules.events.info

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.events.EventBinder
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.view_event.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class EventInfoView(context: Context, initialState: EventInfoVS) :
    BaseMviView<EventInfoVIA, EventInfoVOA, EventInfoVS>(context, initialState) {

    val eventInfoViewModel: EventInfoViewModel by inject { parametersOf(initialState) }

    override fun layoutId() = R.layout.view_event

    override fun viewModel() = eventInfoViewModel

    override fun initView(savedViewState: Bundle?) {
    }

    override fun loadAction() = EventInfoVIA.Init

    override fun inputActions() = listOf(
        btn_participants.clicks().map { EventInfoVIA.Participants },
        btn_apply.clicks().map { EventInfoVIA.EventAction }
    )

    override fun outputActions() = listOf(btn_back.clicks().map { EventInfoVOA.Back })

    override fun bindState(viewState: EventInfoVS) {
        with(viewState)
        {
            event.bind {
                EventBinder.bindEvent(this@EventInfoView, it)
                btn_apply.setImageResource(
                    if (it.accept) R.drawable.btn_accept else R.drawable.btn_accepted
                )
            }
            error.bind {
                showSnackbar(it)
            }
            progressBar.isVisible = progress
            participantsAction.bind {
                outcomingAction(EventInfoVOA.EventMembers(event.peekContent()!!.id))
            }
        }
    }
}