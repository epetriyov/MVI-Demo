package com.connect.android.client.modules.contacts

import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class ContactsVIA : ViewInputAction()

sealed class ContactsVOA : ViewOutputAction()

@Parcelize
class ContactsVS : ViewState()