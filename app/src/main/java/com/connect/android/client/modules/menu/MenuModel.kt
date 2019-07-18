package com.connect.android.client.modules.menu

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class MenuVIA : ViewInputAction()

sealed class MenuVOA : ViewOutputAction()

@Parcelize
class MenuVS : ViewState()