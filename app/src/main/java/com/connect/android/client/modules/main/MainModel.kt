package com.connect.android.client.modules.main

import com.connect.android.client.modules.base.*
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
    val selectFirstTab: ORField<ESO> = ORField.empty(),
    val selectedTabId: ORField<Int> = ORField.empty(),
    val navigateToTab: ORField<ESO> = ORField.empty()
) : ViewState()