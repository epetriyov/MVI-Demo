package com.connect.android.client.modules.start

import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class StartVIA : ViewInputAction(){
    object Init: StartVIA()
    data class Route(val isAuthorized: Boolean): StartVIA()
}

sealed class StartVOA : ViewOutputAction(){
    object MainAction: StartVOA()
    object LoginAction: StartVOA()
}

@Parcelize
data class StartVS(val isAuthorized: ORField<Boolean> = ORField.empty()) : ViewState()