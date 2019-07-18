package com.connect.android.client.modules.recommendations

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class RecommendationsVIA : ViewInputAction()

sealed class RecommendationsVOA : ViewOutputAction()

@Parcelize
class RecommendationsVS : ViewState()