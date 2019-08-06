package com.connect.android.client.model.messages

import com.tinder.scarlet.WebSocket.Event
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.cast
import java.util.concurrent.TimeUnit

interface ChatEngine {
    fun startObservingMessages()
    fun stopObservingMessages()
}

class ChatEngineImpl(private val chatApi: ChatApi, private val messageDao: MessageDao) : ChatEngine {

    companion object {
        private const val MESSAGE_CREATE_TYPE = "messageCreate"
        private const val PING = "ping"
        private const val STOP = "stop"
        private const val PING_TIMEOUT = 10L
    }

    private var disposable: CompositeDisposable = CompositeDisposable()

    private var isConnectedOpened: Boolean = false

    override fun startObservingMessages() {
        chatApi.observeMessages()
            .filter { it.type == MESSAGE_CREATE_TYPE }
            .flatMapCompletable { messageDao.insertMessage(it) }
            .toObservable<Unit>()
            .subscribe().addTo(disposable)
        chatApi.observeWebSocketEvent()
            .filter { it is com.tinder.scarlet.Event.OnWebSocket.Event<*> }
            .cast<com.tinder.scarlet.Event.OnWebSocket.Event<*>>()
            .publish {
                Flowable.merge(
                    it.filter { it.event is Event.OnConnectionOpened<*> }
                        .doOnNext { isConnectedOpened = true }
                        .switchMap {
                            Flowable.interval(PING_TIMEOUT, TimeUnit.SECONDS)
                                .doOnNext { sendMessage(PING) }
                        },
                    it.filter { it.event !is Event.OnConnectionOpened<*> && it.event !is Event.OnMessageReceived }
                        .doOnNext { isConnectedOpened = false }
                )
            }
            .subscribe().addTo(disposable)
    }

    override fun stopObservingMessages() {
        sendMessage(STOP)
        disposable.clear()
        isConnectedOpened = false
    }

    private fun sendMessage(text: String) {
        if (isConnectedOpened) {
            chatApi.sendMessage(MessageToSend(text))
        }
    }
}