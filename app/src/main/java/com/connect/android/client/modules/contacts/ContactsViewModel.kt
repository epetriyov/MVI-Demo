package com.connect.android.client.modules.contacts

import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.model.contacts.ContactsRepository
import com.connect.android.client.model.events.EventsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

typealias ContactSideEffect = SideEffect<ContactsVS, ContactsVIA>

class ContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val eventsRepository: EventsRepository,
    private val chatRepository: ChatsRepository,
    initialState: ContactsVS
) :
    BaseMviViewModel<ContactsVIA, ContactsVS>(initialState) {

    private val loadUsers: ContactSideEffect = { actions, viewState ->
        actions.ofType<ContactsVIA.LoadRequest>()
            .switchMap {
                (if (viewState().eventId == null) {
                    contactsRepository.loadContacts(it.query).toObservable()
                } else {
                    eventsRepository.loadEventMembers(it.query)
                })
                    .map { ContactsVIA.UsersLoaded(it) }
            }
    }

    private val fetchUsers: ContactSideEffect = { actions, viewState ->
        actions.ofType<ContactsVIA.FetchUsers>()
            .switchMap {
                (if (viewState().eventId == null) {
                    contactsRepository.updateContacts()
                } else {
                    eventsRepository.fetchEventMembers(viewState().eventId!!)
                })
                    .andThen(Observable.just(Unit))
                    .map { ContactsVIA.LoadSuccess as ContactsVIA }
                    .onErrorReturn { t -> ContactsVIA.LoadError(t.localizedMessage) }
                    .startWith(ContactsVIA.LoadProgress)
            }
    }

    private val initialLoad: ContactSideEffect = { actions, _ ->
        actions.ofType<ContactsVIA.Init>().publish {
            Observable.merge(
                Observable.just(ContactsVIA.LoadRequest()),
                Observable.just(ContactsVIA.FetchUsers)
            )
        }
    }

    private val createChat: ContactSideEffect = { actions, _ ->
        actions.ofType<ContactsVIA.ChatRequest>().flatMap {
            chatRepository.createChat(it.user)
                .toObservable()
                .map { ContactsVIA.ChatCreated(it) as ContactsVIA }
                .onErrorReturn { t -> ContactsVIA.LoadError(t.localizedMessage) }
                .startWith(ContactsVIA.LoadProgress)
        }
    }

    override fun filterActions() = listOf(
        ContactsVIA.Init::class,
        ContactsVIA.LoadProgress::class,
        ContactsVIA.LoadSuccess::class,
        ContactsVIA.ChatCreated::class,
        ContactsVIA.LoadError::class,
        ContactsVIA.UsersLoaded::class
    )

    override fun reducer(state: ContactsVS, action: ContactsVIA): ContactsVS {
        return when (action) {
            ContactsVIA.LoadProgress -> state.copy(progress = true)
            ContactsVIA.LoadSuccess -> state.copy(progress = false)
            is ContactsVIA.ChatCreated -> state.copy(progress = false, chatCreated = action.chat.withUpdate())
            is ContactsVIA.LoadError -> state.copy(progress = false, error = action.error.withUpdate())
            is ContactsVIA.UsersLoaded -> state.copy(contacts = action.users.withUpdate())
            else -> state
        }
    }

    override fun sideEffects() = listOf(loadUsers, fetchUsers, initialLoad, createChat)
}