package com.connect.android.client.modules.recommendations

import com.connect.android.client.model.chat.ChatRepository
import com.connect.android.client.model.recommendations.RecommendationsRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.profile.ProfileVIA
import com.connect.android.client.modules.profile.ProfileVS
import com.freeletics.rxredux.SideEffect

class RecommendationsViewModel(
    private val recommendationsRepository: RecommendationsRepository,
    private val chatRepository: ChatRepository,
    initialState: ProfileVS
) : BaseMviViewModel<ProfileVIA, ProfileVS>(initialState) {
    override fun reducer(state: ProfileVS, action: ProfileVIA): ProfileVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<ProfileVS, ProfileVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}