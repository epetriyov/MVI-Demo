package com.connect.android.client.modules.messages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.connect.android.client.Constants.SEARCH_THROTTLE_DELAY
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.view_screen_with_list.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

@SuppressLint("ViewConstructor")
class MessagesView(context: Context, initialState: MessagesVS) :
    BaseMviView<MessagesVIA, MessagesVOA, MessagesVS>(context, initialState) {

    val messagesViewModel: MessagesViewModel by inject { parametersOf(initialState) }

    val chatsAdapterAdapter: ChatsAdapter by inject { parametersOf(context) }

    val chatsLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    override fun layoutId(): Int = R.layout.view_screen_with_list

    override fun viewModel() = messagesViewModel

    override fun initView(savedViewState: Bundle?) {
        recyclerView.layoutManager = chatsLayoutManager
        recyclerView.adapter = chatsAdapterAdapter
    }

    override fun loadAction() = MessagesVIA.Init

    override fun inputActions() = listOf(
        edit_search.textChanges().throttleLast(SEARCH_THROTTLE_DELAY, TimeUnit.SECONDS)
            .map { MessagesVIA.LoadRequest(it.toString()) })

    override fun outputActions() = listOf(chatsAdapterAdapter.itemSelections().map { MessagesVOA.MessageSelect(it) })

    override fun bindState(viewState: MessagesVS) {
        with(viewState) {
            error.bind {
                showSnackbar(it)
            }
            chats.bind {
                chatsAdapterAdapter.submitList(it)
            }
            progressBar.isVisible = viewState.progress
        }
    }
}