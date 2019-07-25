package com.connect.android.client.modules.contacts

import com.connect.android.client.model.chat.ChatRepository
import com.connect.android.client.model.contacts.ContactsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect

class ContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val chatRepository: ChatRepository,
    initialState: ContactsVS
) :
    BaseMviViewModel<ContactsVIA, ContactsVS>(initialState) {
    override fun reducer(state: ContactsVS, action: ContactsVIA): ContactsVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<ContactsVS, ContactsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}