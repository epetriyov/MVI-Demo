package com.connect.android.client.model.contacts

import com.connect.android.client.model.profile.User
import io.reactivex.Flowable

interface ContactsRepository {

    fun fetchContacts(): Flowable<List<User>>
}