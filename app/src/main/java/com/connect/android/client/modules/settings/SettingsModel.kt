package com.connect.android.client.modules.settings

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class SettingsVIA : ViewInputAction()

sealed class SettingsVOA : ViewOutputAction()

@Parcelize
class SettingsVS : ViewState()