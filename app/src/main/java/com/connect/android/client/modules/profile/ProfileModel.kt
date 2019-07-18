package com.connect.android.client.modules.profile

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class ProfileVIA : ViewInputAction()

sealed class ProfileVOA : ViewOutputAction()

@Parcelize
class ProfileVS : ViewState()