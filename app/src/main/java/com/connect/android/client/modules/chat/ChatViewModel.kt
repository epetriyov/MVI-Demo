package com.connect.android.client.modules.chat

import android.text.TextUtils
import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.model.messages.ChatEngine
import com.connect.android.client.model.messages.MessagesRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import kotlin.reflect.KClass

typealias ChatSideEffect = SideEffect<ChatVS, ChatVIA>

class ChatViewModel(
    private val messagesRepository: MessagesRepository,
    private val chatEngine: ChatEngine,
    private val authRepository: AuthRepository,
    initialState: ChatVS
) : BaseMviViewModel<ChatVIA, ChatVS>(initialState) {

    companion object {
        private const val MESSAGE_LIMIT = 300
    }

    private val loadMessages: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.Init>().flatMap {
            messagesRepository.getMessages(viewState().chat.peekContent()!!.id)
                .map {
                    it.map {
                        DisplayableMessage(
                            it.id,
                            it.text,
                            it.creationDate,
                            authRepository.getUserId() == it.userId
                        )
                    }
                }
                .toObservable()
                .map { ChatVIA.MessagesLoaded(it) }
        }
    }

    private val loadNext: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.LoadNext>().flatMap {
            messagesRepository.loadChatMessages(
                viewState().chat.peekContent()!!.id,
                viewState().messages.peekContent()?.size ?: 0, MESSAGE_LIMIT
            )
                .andThen(Observable.just(Unit))
                .map { ChatVIA.NextLoaded as ChatVIA }
                .onErrorReturn { t -> ChatVIA.NextLoadError(t.localizedMessage) }
                .startWith(ChatVIA.NextLoadProgress)
        }
    }

    private val connectMessages: ChatSideEffect = { actions, _ ->
        actions.ofType<ChatVIA.StartObserve>().doOnNext {
            chatEngine.startObservingMessages()
        }.map { ChatVIA.ObserveStarted }
    }

    private val disconnectMessages: ChatSideEffect = { actions, _ ->
        actions.ofType<ChatVIA.StopObserve>().doOnNext {
            chatEngine.stopObservingMessages()
        }.map { ChatVIA.ObserveStopped }
    }

    private val sendMessage: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.SendAction>().flatMap { action ->
            messagesRepository.sendMessage(viewState().chat.peekContent()!!.id, action.message)
                .andThen(Observable.just(Unit))
                .map { ChatVIA.MessageSend as ChatVIA }
                .onErrorReturn { t -> ChatVIA.SendError(t.localizedMessage) }
                .startWith(ChatVIA.SendProgress)
        }
    }

    override fun filterActions(): List<KClass<out ChatVIA>> = listOf(
        ChatVIA.ProfileClickedAction::class,
        ChatVIA.SendProgress::class,
        ChatVIA.SendError::class,
        ChatVIA.MessageSend::class,
        ChatVIA.NextLoadProgress::class,
        ChatVIA.NextLoadError::class,
        ChatVIA.NextLoaded::class,
        ChatVIA.MessagesLoaded::class,
        ChatVIA.TextChanged::class
    )

    override fun reducer(state: ChatVS, action: ChatVIA): ChatVS {
        return when (action) {
            ChatVIA.ProfileClickedAction -> state.copy(profileCLicked = Unit.withUpdate())
            ChatVIA.SendProgress -> state.copy(isSending = true)
            is ChatVIA.SendError -> state.copy(sendError = action.error.withUpdate())
            ChatVIA.MessageSend -> state.copy(isSending = false, messageSent = Unit.withUpdate())
            ChatVIA.NextLoadProgress -> state.copy(isLoadingNext = true)
            is ChatVIA.NextLoadError -> state.copy(nextLoadError = action.error.withUpdate())
            ChatVIA.NextLoaded -> state.copy(isLoadingNext = false)
            is ChatVIA.MessagesLoaded -> state.copy(messages = action.messages.withUpdate())
            is ChatVIA.TextChanged -> state.copy(buttonEnabled = !TextUtils.isEmpty(action.text))
            else -> state
        }
    }

    override fun sideEffects(): List<SideEffect<ChatVS, ChatVIA>> = listOf(
        loadMessages, loadNext, connectMessages, disconnectMessages, sendMessage
    )
}