package com.connect.android.client.modules.chat

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.model.messages.ChatEngine
import com.connect.android.client.model.messages.MessagesRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
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
        actions.ofType<ChatVIA.Init>()
            .observeOn(Schedulers.io())
            .flatMap {
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

    private val initialRefresh: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.Init>().map { ChatVIA.LoadNext }
    }

    private val loadNext: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.LoadNext>().flatMap {
            messagesRepository.loadChatMessages(
                viewState().chat.peekContent()!!.id,
                viewState().messages.peekContent()?.size ?: 0, MESSAGE_LIMIT
            )
                .andThen(Observable.fromCallable { Unit })
                .map { ChatVIA.NextLoaded as ChatVIA }
                .onLoggableError { t -> ChatVIA.NextLoadError(t.safeMessage()) }
                .startWith(ChatVIA.NextLoadProgress)
        }
    }

    private val connectMessages: ChatSideEffect = { actions, _ ->
        actions.ofType<ChatVIA.Init>().doOnNext {
            chatEngine.startObservingMessages()
        }.map { ChatVIA.ObserveStarted }
            .doOnDispose { chatEngine.stopObservingMessages() }
    }

    private val sendMessage: ChatSideEffect = { actions, viewState ->
        actions.ofType<ChatVIA.SendAction>()
            .observeOn(Schedulers.io())
            .flatMap { action ->
            messagesRepository.sendMessage(viewState().chat.peekContent()!!.id, action.message)
                .andThen(Observable.fromCallable { Unit })
                .map { ChatVIA.MessageSend as ChatVIA }
                .onLoggableError { t -> ChatVIA.SendError(t.safeMessage()) }
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
            ChatVIA.ProfileClickedAction -> state.copy(profileCLicked = ESO.withUpdate())
            ChatVIA.SendProgress -> state.copy(isSending = true)
            is ChatVIA.SendError -> state.copy(sendError = action.error.withUpdate())
            ChatVIA.MessageSend -> state.copy(isSending = false, messageSent = ESO.withUpdate())
            ChatVIA.NextLoadProgress -> state.copy(isLoadingNext = true)
            is ChatVIA.NextLoadError -> state.copy(nextLoadError = action.error.withUpdate())
            ChatVIA.NextLoaded -> state.copy(isLoadingNext = false)
            is ChatVIA.MessagesLoaded -> state.copy(messages = action.messages.withUpdate())
            is ChatVIA.TextChanged -> state.copy(buttonEnabled = action.text.isNotEmpty())
            else -> state
        }
    }

    override fun sideEffects(): List<SideEffect<ChatVS, ChatVIA>> = listOf(
        loadMessages, initialRefresh, loadNext, connectMessages, sendMessage
    )
}