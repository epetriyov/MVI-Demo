package com.connect.android.client.model

/**
 * Created by evgenii on 6/27/17.
 */
open interface ProfileItem {
    fun getIdentifier(): String?
    fun getFirstText(): String?
    fun getSecondText(): String?
    fun copyFrom(item: ProfileItem)
}