package com.connect.android.client.modules.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

abstract class ViewInputAction

abstract class ViewOutputAction

abstract class ViewState : Parcelable

@Parcelize
class EmptyViewState : ViewState()

object ESO: Serializable