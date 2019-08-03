package com.connect.android.client.model.contacts

import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface ContactsRepository {

    fun loadContacts(): Flowable<List<User>>

    fun updateContacts(): Completable

    fun getContactsByName(searchText: String): List<User>
}

class ContactsRepoImpl(private val contactsApi: ContactsApi, private val contactsDao: ContactsDao) :
    ContactsRepository {
    override fun updateContacts(): Completable {
        return contactsApi.getContacts().map { it.data }.flatMapCompletable { contactsDao.insertContacts(it) }
    }

    override fun getContactsByName(searchText: String): List<User> {
        return contactsDao.getContacts(searchText)
    }

    override fun loadContacts(): Flowable<List<User>> {
        return contactsDao.getContacts()
    }

}