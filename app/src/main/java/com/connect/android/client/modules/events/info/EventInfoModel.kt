package com.connect.android.client.modules.events.info

import com.connect.android.client.model.events.Event
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class EventInfoVIA : ViewInputAction() {
    object Init : EventInfoVIA()
    object Participants : EventInfoVIA()
    object ActionDone : EventInfoVIA()
    object ActionProgress : EventInfoVIA()
    object EventAction : EventInfoVIA()
    data class Error(val error: String) : EventInfoVIA()
}

sealed class EventInfoVOA : ViewOutputAction() {
    data class EventMembers(val eventId: String) : EventInfoVOA()
    object Back : EventInfoVOA()
}

@Parcelize
data class EventInfoVS(
    val event: ORField<Event> = ORField.empty(),
    val progress: Boolean = false,
    val error: ORField<String> = ORField.empty(),
    val participantsAction: ORField<Unit> = ORField.empty()
) : ViewState()