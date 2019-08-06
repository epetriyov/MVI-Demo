package com.connect.android.client.model.common

import java.io.Serializable

/**
 * Created by evgenii on 6/27/17.
 */
interface ProfileItem : Serializable {
    fun getFirstText(): String?
    fun getSecondText(): String?
    fun getDates(): String
    fun copyFrom(item: ProfileItem)
    fun getIdentifier(): String?
}