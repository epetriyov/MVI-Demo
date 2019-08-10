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
            else " WHERE name GLOB '*' || :query|| '*' OR about GLOB '*' || :query|| '*' OR skills GLOB '*' || :query|| '*' OR works GLOB '*' || :query|| '*'")
        val args = if (searchText.isNullOrEmpty()) emptyArray() else arrayOf(searchText)
        val sqlLiteQuery = SimpleSQLiteQuery(sql, args)
        return contactsDao.getContacts(sqlLiteQuery)
    }

}