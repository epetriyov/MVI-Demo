package com.connect.android.client.modules.recommendations

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.profile.ProfileController

class RecommendationsController(bundle: Bundle? = null) :
    BaseMviController<RecommendationsView, RecommendationsVIA, RecommendationsVOA>(bundle) {
    override fun buildView(context: Context): RecommendationsView = RecommendationsView(context, RecommendationsVS())

    override fun handleViewEvents(action: RecommendationsVOA) {
        Do exhaustive when (action) {
            is RecommendationsVOA.OpenProfile -> {
                router.pushController(ProfileController.newInstance(action.user).buildRouterTransaction())
            }
            is RecommendationsVOA.OpenChat ->
                //TODO open chat
                targetController
        }
    }
}