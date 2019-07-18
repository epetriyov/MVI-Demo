package com.connect.android.client.modules.myprofile

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class MyProfileVIA : ViewInputAction()

sealed class MyProfileVOA : ViewOutputAction()

@Parcelize
class MyProfileVS : ViewState()