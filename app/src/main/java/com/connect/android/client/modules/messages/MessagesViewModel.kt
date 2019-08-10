package com.connect.android.client.modules.messages

import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

typealias MessagesSideEffect = SideEffect<MessagesVS, MessagesVIA>

class MessagesViewModel(private val chatsRepository: ChatsRepository, initialState: MessagesVS) :
    BaseMviViewModel<MessagesVIA, MessagesVS>(initialState) {

    private val loadChats: MessagesSideEffect = { actions, _ ->
        actions.ofType<MessagesVIA.LoadRequest>()
            .switchMap {
                chatsRepository.getChats(it.query)
                    .toObservable()
                    .map { MessagesVIA.ChatsLoaded(it) }
            }
    }

    private val fetchChats: MessagesSideEffect = { actions, _ ->
        actions.ofType<MessagesVIA.FetchData>()
            .switchMap {
                chatsRepository.updateChats()
                    .andThen(Observable.just(Unit))
                    .map { MessagesVIA.FetchSuccess as MessagesVIA }
                    .onErrorReturn { t -> MessagesVIA.FetchError(t.localizedMessage) }
                    .startWith(MessagesVIA.FetchProgress)
            }
    }

    private val initialLoad: MessagesSideEffect = { actions, _ ->
        actions.ofType<MessagesVIA.Init>().publish {
            Observable.merge(
                Observable.just(MessagesVIA.LoadRequest()),
                Observable.just(MessagesVIA.FetchData)
            )
        }
    }


    override fun filterActions() = listOf(
        MessagesVIA.FetchProgress::class,
        MessagesVIA.FetchError::class,
        MessagesVIA.FetchSuccess::class,
        MessagesVIA.ChatsLoaded::class
    )

    override fun reducer(state: MessagesVS, action: MessagesVIA): MessagesVS {
        return when (action) {
            MessagesVIA.FetchProgress -> state.copy(progress = true)
            MessagesVIA.FetchSuccess -> state.copy(progress = false)
            is MessagesVIA.FetchError -> state.copy(progress = false, error = action.error.withUpdate())
            is MessagesVIA.ChatsLoaded -> state.copy(chats = action.chats.withUpdate())
            else -> state
        }
    }

    override fun sideEffects() = listOf(initialLoad, loadChats, fetchChats)
}