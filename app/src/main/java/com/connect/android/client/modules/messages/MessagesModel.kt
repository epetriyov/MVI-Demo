package com.connect.android.client.modules.messages

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class MessagesVIA : ViewInputAction()

sealed class MessagesVOA : ViewOutputAction()

@Parcelize
class MessagesVS : ViewState()