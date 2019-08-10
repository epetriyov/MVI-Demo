package com.connect.android.client.modules.events

import com.connect.android.client.model.events.Event
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class EventsVIA : ViewInputAction() {
    data class LoadRequest(val query: String? = null, val selectedTabPosition: Int = 0) : EventsVIA()
    data class EventsLoaded(val events: List<Event>) : EventsVIA()
    data class Error(val error: String) : EventsVIA()
    data class EventAction(val event: Event) : EventsVIA()
    object UpdateProgress : EventsVIA()
    object EventsUpdated : EventsVIA()
    object Init : EventsVIA()
    object RefreshEvents : EventsVIA()
}

sealed class EventsVOA : ViewOutputAction() {
    data class EventMembers(val eventId: String) : EventsVOA()
    data class EventInfo(val event: Event) : EventsVOA()
}

@Parcelize
data class EventsVS(
    val events: ORField<List<Event>> = ORField.empty(),
    val progress: Boolean = false,
    val error: ORField<String> = ORField.empty()
) : ViewState()