package com.connect.android.client.modules.events

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class EventsVIA : ViewInputAction()

sealed class EventsVOA : ViewOutputAction()

@Parcelize
class EventsVS : ViewState()