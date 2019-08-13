package com.connect.android.client.modules.contacts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.connect.android.client.Constants.SEARCH_THROTTLE_DELAY
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.view_screen_with_list.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

@SuppressLint("ViewConstructor")
class ContactsView(context: Context, initialState: ContactsVS) :
    BaseMviView<ContactsVIA, ContactsVOA, ContactsVS>(context, initialState) {

    val contactsViewModel: ContactsViewModel by inject { parametersOf(initialState) }

    val contactsAdapter: ContactsAdapter by inject { parametersOf(context) }

    val contactsLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    override fun layoutId() = R.layout.view_screen_with_list

    override fun viewModel() = contactsViewModel

    override fun initView(savedViewState: Bundle?) {
        with(recyclerView)
        {
            layoutManager = contactsLayoutManager
            adapter = contactsAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    .apply {
                        setDrawable(ContextCompat.getDrawable(context, R.drawable.space_divider)!!)
                    })
        }
    }

    override fun inputActions() = listOf(
        edit_search.textChanges().throttleLast(SEARCH_THROTTLE_DELAY, TimeUnit.SECONDS)
            .map { ContactsVIA.LoadRequest(it.toString()) },
        contactsAdapter.chats().map { ContactsVIA.ChatRequest(it) }
    )

    override fun outputActions() = listOf(
        btn_back.clicks().map { ContactsVOA.Back },
        contactsAdapter.itemSelections().map { ContactsVOA.UserSelect(it) }
    )

    override fun loadAction() = ContactsVIA.Init

    override fun bindState(viewState: ContactsVS) {
        with(viewState) {
            title_contacts.text =
                if (eventId != null) resources.getString(R.string.members) else resources.getString(R.string.title_contacts)
            btn_back.isVisible = eventId != null
            chatCreated.bind {
                outcomingAction(ContactsVOA.ChatCreate(it))
            }
            error.bind {
                showSnackbar(it)
            }
            contacts.bind {
                contactsAdapter.submitList(it)
            }
            progressBar.isVisible = viewState.progress
        }
    }
}