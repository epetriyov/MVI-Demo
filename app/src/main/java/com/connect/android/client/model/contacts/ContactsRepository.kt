package com.connect.android.client.model.contacts

import com.connect.android.client.model.profile.User
import io.reactivex.Flowable

interface ContactsRepository {

    fun fetchContacts(): Flowable<List<User>>

    fun getContactsByName(): Flowable<List<User>>
}

class ContactsRepoImpl(private val contactsApi: ContactsApi, private val contactsDao: ContactsDao) :
    ContactsRepository {
    override fun getContactsByName(): Flowable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchContacts(): Flowable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}