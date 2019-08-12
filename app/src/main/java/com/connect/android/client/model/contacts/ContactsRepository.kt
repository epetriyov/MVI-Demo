package com.connect.android.client.model.contacts

import androidx.sqlite.db.SimpleSQLiteQuery
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface ContactsRepository {

    fun loadContacts(searchText: String?): Flowable<List<User>>

    fun updateContacts(): Completable
}

class ContactsRepoImpl(private val contactsApi: ContactsApi, private val contactsDao: ContactsDao) :
    ContactsRepository {
    override fun updateContacts(): Completable {
        return contactsApi.getContacts().map { it.data }
            .flatMapCompletable {
                contactsDao.deleteContacts(contactsDao.getAllContacts())
                    .andThen(contactsDao.insertContacts(it))
            }
    }

    override fun loadContacts(searchText: String?): Flowable<List<User>> {
        val sql =
            "SELECT * FROM contacts" + (if (searchText.isNullOrEmpty()) ""
            else " WHERE UPPER(name) GLOB UPPER('*$searchText*') OR UPPER(about) GLOB UPPER('*$searchText*') " +
                    "OR UPPER(skills) GLOB UPPER('*$searchText*') OR UPPER(works) GLOB UPPER('*$searchText*')")
        val args =
//            if (searchText.isNullOrEmpty())
                emptyArray<Any>()
//            else arrayOf(searchText)
        val sqlLiteQuery = SimpleSQLiteQuery(sql, args)
        return contactsDao.getContacts(sqlLiteQuery)
    }

}