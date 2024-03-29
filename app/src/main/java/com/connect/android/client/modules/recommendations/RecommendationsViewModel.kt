package com.connect.android.client.modules.recommendations

import android.Manifest
import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.model.location.LocationRepository
import com.connect.android.client.model.recommendations.ConnectState.*
import com.connect.android.client.model.recommendations.RecommendationsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import kotlin.reflect.KClass

typealias RecommendationsSideEffect = SideEffect<RecommendationsVS, RecommendationsVIA>

class RecommendationsViewModel(
    private val recommendationsRepository: RecommendationsRepository,
    private val locationRepository: LocationRepository,
    private val chatsRepository: ChatsRepository,
    private val rxPermissions: RxPermissions,
    initialState: RecommendationsVS
) : BaseMviViewModel<RecommendationsVIA, RecommendationsVS>(initialState) {

    private val init: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.Init>()
            .flatMap { rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION) }
            .publish {
                Observable.merge(
                    it.filter { it }.map { RecommendationsVIA.SendLocation },
                    it.filter { !it }.map { RecommendationsVIA.OpenLocationPermission }
                )
            }
    }

    private val sendLocation: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.SendLocation>()
            .observeOn(Schedulers.io())
            .switchMap {
                locationRepository.updateCurrentLocation()
                    .andThen(Observable.fromCallable { Unit })
                    .map { RecommendationsVIA.GetRecommendations as RecommendationsVIA }
                    .onLoggableError { t -> RecommendationsVIA.Error(t.safeMessage()) }
                    .startWith(RecommendationsVIA.Progress)
            }
    }

    private val getRecommendations: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.GetRecommendations>()
            .observeOn(Schedulers.io())
            .switchMap {
                recommendationsRepository.getRecommendations()
                    .toObservable()
                    .map { RecommendationsVIA.Recommendations(it) as RecommendationsVIA }
                    .onLoggableError { t -> RecommendationsVIA.Error(t.safeMessage()) }
            }
    }

    private val connectUser: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.UserConnect>()
            .observeOn(Schedulers.io())
            .switchMap { action ->
                recommendationsRepository.connectUser(action.user.id)
                    .toObservable()
                    .map {
                        when (it) {
                            CONNECTED -> RecommendationsVIA.Disconnected as RecommendationsVIA
                            CONNECTING -> RecommendationsVIA.Connecting
                            DECLINED -> RecommendationsVIA.Disconnected
                        }
                    }
                    .onLoggableError { t -> RecommendationsVIA.Error(t.safeMessage()) }
            }
    }

    private val disconnectUser: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.UserDisconnect>()
            .observeOn(Schedulers.io())
            .switchMap { action ->
                recommendationsRepository.declineUser(action.userId)
                    .toObservable()
                    .map { RecommendationsVIA.Disconnected as RecommendationsVIA }
                    .onLoggableError { t -> RecommendationsVIA.Error(t.safeMessage()) }
            }
    }

    private val startChat: RecommendationsSideEffect = { actions, _ ->
        actions.ofType<RecommendationsVIA.StartChat>()
            .observeOn(Schedulers.io())
            .switchMap { action ->
                chatsRepository.createChat(action.user)
                    .toObservable()
                    .map { RecommendationsVIA.ChatCreated(it) as RecommendationsVIA }
                    .onLoggableError { t -> RecommendationsVIA.Error(t.safeMessage()) }
            }
    }

    private val startChatConnected: RecommendationsSideEffect = { actions, viewState ->
        actions.ofType<RecommendationsVIA.StartChatConnected>()
            .filter { viewState().connected.peekContent() != null }
            .map { RecommendationsVIA.StartChat(viewState().connected.peekContent()!!) }
    }

    override fun filterActions(): List<KClass<out RecommendationsVIA>> = listOf(
        RecommendationsVIA.OpenLocationPermission::class,
        RecommendationsVIA.Progress::class,
        RecommendationsVIA.ChatCreated::class,
        RecommendationsVIA.Connected::class,
        RecommendationsVIA.Recommendations::class,
        RecommendationsVIA.Error::class
    )

    override fun reducer(state: RecommendationsVS, action: RecommendationsVIA): RecommendationsVS {
        return when (action) {
            RecommendationsVIA.OpenLocationPermission -> state.copy(showLocationPermission = true, progress = false)
            RecommendationsVIA.Progress -> state.copy(showLocationPermission = false, progress = true)
            is RecommendationsVIA.ChatCreated -> state.copy(openChat = action.chat.withUpdate())
            is RecommendationsVIA.Connected -> state.copy(connected = action.user.withUpdate())
            is RecommendationsVIA.Recommendations -> state.copy(
                showLocationPermission = false,
                progress = false,
                recommendations = action.recommendations.withUpdate()
            )
            is RecommendationsVIA.Error -> state.copy(
                showLocationPermission = false,
                progress = false,
                error = action.error.withUpdate()
            )
            else -> state
        }
    }

    override fun sideEffects(): List<RecommendationsSideEffect> = listOf(
        init, sendLocation, getRecommendations, connectUser, disconnectUser, startChat, startChatConnected
    )
}