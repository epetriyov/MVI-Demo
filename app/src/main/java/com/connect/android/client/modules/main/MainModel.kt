package com.connect.android.client.modules.main

import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class MainVIA : ViewInputAction() {
    object Init : MainVIA()
    object TokenUpdated: MainVIA()
    object FirstTabSelect : MainVIA()
    data class TabClicked(val tabId: Int) : MainVIA()
    data class TabSelect(val tabId: Int): MainVIA()
}

sealed class MainVOA : ViewOutputAction(){
    object Recommendations: MainVOA()
    object Events: MainVOA()
    object Contacts: MainVOA()
    object Chats: MainVOA()
    object Profile: MainVOA()
}

@Parcelize
data class MainVS(
    val selectFirstTab: ORField<Unit> = ORField.empty(),
    val selectedTabId: ORField<Int> = ORField.empty(),
    val navigateToTab: ORField<Unit> = ORField.empty()
) : ViewState()