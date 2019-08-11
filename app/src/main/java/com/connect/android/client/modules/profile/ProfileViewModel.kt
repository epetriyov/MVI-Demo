package com.connect.android.client.modules.profile

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.model.recommendations.RecommendationsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.rxkotlin.ofType
import kotlin.reflect.KClass

typealias ProfileSideEffect = SideEffect<ProfileVS, ProfileVIA>

class ProfileViewModel(
    private val recommendationsRepository: RecommendationsRepository,
    private val chatsRepository: ChatsRepository,
    initialState: ProfileVS
) : BaseMviViewModel<ProfileVIA, ProfileVS>(initialState) {

    private val createChat: ProfileSideEffect = { actions, viewState ->
        actions.ofType<ProfileVIA.Chat>()
            .flatMap {
                chatsRepository.createChat(viewState().user.peekContent()!!)
                    .toObservable()
                    .map { ProfileVIA.ChatCreated(it) as ProfileVIA }
                    .onLoggableError { t -> ProfileVIA.ChatCreateError(t.safeMessage()) }
                    .startWith(ProfileVIA.ChatCreateProgress)
            }
    }


    override fun filterActions(): List<KClass<out ProfileVIA>> =
        listOf(
            ProfileVIA.Init::class, ProfileVIA.ChatCreated::class,
            ProfileVIA.ChatCreateError::class, ProfileVIA.ChatCreateProgress::class
        )

    override fun reducer(state: ProfileVS, action: ProfileVIA): ProfileVS {
        return when (action) {
            is ProfileVIA.ChatCreated -> state.copy(chatCreateProgress = false, chat = action.chat.withUpdate())
            is ProfileVIA.ChatCreateError -> state.copy(
                error = action.errorMessage.withUpdate(),
                chatCreateProgress = false
            )
            ProfileVIA.ChatCreateProgress -> state.copy(chatCreateProgress = true)
            else -> state
        }
    }

    override fun sideEffects(): List<SideEffect<ProfileVS, ProfileVIA>> = listOf(createChat)
}