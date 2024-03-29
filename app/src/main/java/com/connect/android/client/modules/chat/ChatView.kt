package com.connect.android.client.modules.chat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.connect.android.client.R
import com.connect.android.client.extensions.scrollsToEnd
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.keyboard.KeyboardHeightObserver
import com.connect.android.client.keyboard.KeyboardHeightProvider
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.tools.glide.GlideApp
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_chat.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@SuppressLint("ViewConstructor")
class ChatView(context: Context, initialState: ChatVS) : BaseMviView<ChatVIA, ChatVOA, ChatVS>(context, initialState) {

    val chatViewModel: ChatViewModel by inject { parametersOf(initialState) }

    val chatLayoutManager: LinearLayoutManager by inject(named("reversed")) { parametersOf(context) }

    val messagesAdapter: MessagesAdapter by inject { parametersOf(context) }

    private lateinit var keyboardHeightProvider: KeyboardHeightProvider

    override fun layoutId() = R.layout.view_chat

    override fun viewModel(): BaseMviViewModel<ChatVIA, ChatVS>? = chatViewModel

    override fun initView(savedViewState: Bundle?) {
        keyboardHeightProvider = KeyboardHeightProvider(context as Activity)
        with(chat_recyclerview) {
            layoutManager = chatLayoutManager
            adapter = messagesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                .apply {
                    setDrawable(ContextCompat.getDrawable(context, R.drawable.space_divider)!!)
                })
        }
        messagesAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                chat_recyclerview.scrollToPosition(0)
            }
        })
    }

    override fun loadAction() = ChatVIA.Init

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        keyboardHeightProvider.setKeyboardHeightObserver(object : KeyboardHeightObserver {
            override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
                if (height > 0) {
                    chat_recyclerview.scrollToPosition(0)
                }
            }
        })
        handler.post {
            keyboardHeightProvider.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        keyboardHeightProvider.close()
    }

    override fun inputActions(): List<Observable<out ChatVIA>> = listOf(
        btnSend.clicks().map { ChatVIA.SendAction(edit_message.text.toString()) },
        img_avatar.clicks().map { ChatVIA.ProfileClickedAction },
        edit_message.textChanges().map { ChatVIA.TextChanged(it.toString()) },
        chat_recyclerview.scrollsToEnd(chatLayoutManager).map { ChatVIA.LoadNext }
    )

    override fun outputActions(): List<Observable<out ChatVOA>> = listOf(
        btn_back.clicks().map { ChatVOA.BackAction }
    )

    override fun bindState(viewState: ChatVS) {
        with(viewState)
        {
            btnSend.isEnabled = buttonEnabled
            chat.bind {
                label_name.text = it.user.name
                GlideApp.with(context)
                    .load(it.user.avatar)
                    .error(R.drawable.ic_placeholder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(img_avatar)
            }
            messages.bind {
                messagesAdapter.submitList(it)
            }
            sendError.bind {
                showSnackbar(it)
            }
            nextLoadError.bind {
                showSnackbar(it)
            }
            profileCLicked.bind {
                outcomingAction(ChatVOA.ProfileAction(chat.peekContent()!!.user))
            }
        }
    }
}