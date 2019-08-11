package com.connect.android.client.modules.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.connect.android.client.R
import com.connect.android.client.extensions.scrollsToEnd
import com.connect.android.client.extensions.showSnackbar
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

    override fun layoutId() = R.layout.view_chat

    override fun viewModel(): BaseMviViewModel<ChatVIA, ChatVS>? = chatViewModel

    override fun initView(savedViewState: Bundle?) {
        with(chat_recyclerview) {
            layoutManager = chatLayoutManager
            adapter = messagesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                .apply {
                    setDrawable(ContextCompat.getDrawable(context, R.drawable.space_divider)!!)
                })
        }
    }

    override fun loadAction() = ChatVIA.Init

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
                label_work.text = it.user.getWorkInfo()
                GlideApp.with(context)
                    .load(it.user.avatar)
                    .centerCrop()
                    .error(R.drawable.ic_placeholder)
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