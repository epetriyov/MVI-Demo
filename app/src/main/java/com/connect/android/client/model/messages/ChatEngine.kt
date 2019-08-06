package com.connect.android.client.model.messages

import io.reactivex.disposables.Disposable

interface ChatEngine {
    fun startObservingMessages()
    fun stopObservingMessages()
}

class ChatEngineImpl(private val chatApi: ChatApi, private val messageDao: MessageDao) : ChatEngine {

    private var disposable: Disposable? = null

    override fun startObservingMessages() {
        disposable = chatApi.observeMessages()
            .flatMapCompletable { messageDao.insertMessage(it) }
            .toObservable<Unit>()
            .subscribe()
    }

    override fun stopObservingMessages() {
        disposable?.dispose()
    }
}