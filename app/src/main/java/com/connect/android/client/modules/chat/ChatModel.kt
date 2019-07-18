package com.connect.android.client.modules.chat

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class ChatVIA : ViewInputAction()

sealed class ChatVOA : ViewOutputAction()

@Parcelize
class ChatVS : ViewState()