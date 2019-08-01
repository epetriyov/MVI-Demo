package com.connect.android.client.model.common

/**
 * Created by evgenii on 6/27/17.
 */
interface ProfileItem {
    fun getFirstText(): String?
    fun getSecondText(): String?
    fun copyFrom(item: ProfileItem)
}