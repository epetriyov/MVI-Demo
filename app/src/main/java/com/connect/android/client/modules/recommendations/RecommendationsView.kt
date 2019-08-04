package com.connect.android.client.modules.recommendations

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.model.profile.ConnectionType
import com.connect.android.client.modules.base.BaseMviView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.card_connected.view.*
import kotlinx.android.synthetic.main.view_location_permission.view.*
import kotlinx.android.synthetic.main.view_recommendations.view.*
import kotlinx.android.synthetic.main.view_recommendations_container.view.*
import kotlinx.android.synthetic.main.view_searching_connects.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class RecommendationsView(context: Context, initialState: RecommendationsVS) :
    BaseMviView<RecommendationsVIA, RecommendationsVOA, RecommendationsVS>(context, initialState) {

    val recommendationsViewModel: RecommendationsViewModel by inject { parametersOf(context, initialState) }

    private val recommendationsHelper: RecommendationsHelper by inject { parametersOf(context) }

    override fun layoutId() = R.layout.view_recommendations_container

    override fun viewModel() = recommendationsViewModel

    override fun loadAction() = RecommendationsVIA.Init

    override fun initView(savedViewState: Bundle?) {
        recommendationsHelper.init(card_stack_view)
        btn_revert.clicks().doOnNext {
            recommendationsHelper.rewind()
        }.subscribe()
        btn_connect.clicks().doOnNext { recommendationsHelper.connect() }.subscribe()
        btn_deny.clicks().map { recommendationsHelper.disconnect() }.subscribe()
        recommendationsHelper.userAppeared().subscribe {
            btn_chat.isEnabled = it.connectionType == ConnectionType.CONNECTED
        }
        btn_close.clicks().subscribe {
            card_connected.isVisible = false
        }
    }

    override fun inputActions() = listOf(
        btn_open_permission.clicks().map { RecommendationsVIA.OpenLocationPermission },
        btn_chat.clicks().map { recommendationsHelper.getCurrentItem() }.map { RecommendationsVIA.StartChat(it) },
        btn_chat_connected.clicks().map { RecommendationsVIA.StartChatConnected },
        recommendationsHelper.connectToUser().map { RecommendationsVIA.UserConnect(it) },
        recommendationsHelper.declineUser().map { RecommendationsVIA.UserDisconnect(it) },
        recommendationsHelper.loadNext().map { RecommendationsVIA.GetRecommendations }
    )

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pulsator.stop()
    }

    override fun outputActions(): List<Observable<out RecommendationsVOA>> =
        listOf(recommendationsHelper.clicks().map { RecommendationsVOA.OpenProfile(it) })

    override fun bindState(viewState: RecommendationsVS) {
        with(viewState)
        {
            searching_panel.isVisible = progress
            location_permissions.isVisible = showLocationPermission
            if (progress) {
                pulsator.start()
            } else {
                pulsator.stop()
            }
            error.bind {
                showSnackbar(it)
            }
            recommendations.bind {
                recommendationsHelper.addRecommendations(it)
            }
            openChat.bind {
                outcomingAction(RecommendationsVOA.OpenChat(it))
            }
            if (connected.isEmpty()) {
                card_connected.isVisible = false
            }
            connected.bind { user ->
                card_connected.isVisible = true
                ViewHolder(card_connected).bindView(user)
            }
        }

    }
}