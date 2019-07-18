package com.connect.android.client.modules.auth

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class AuthVIA : ViewInputAction()

sealed class AuthVOA : ViewOutputAction()

@Parcelize
class AuthVS : ViewState()