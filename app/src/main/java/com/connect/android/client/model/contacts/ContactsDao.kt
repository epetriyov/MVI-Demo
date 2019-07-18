package com.connect.android.client.model.contacts

import androidx.room.*
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(items: List<User>): Completable

    @Delete
    fun deleteContacts(items: List<User>): Completable

    @Query("SELECT * FROM contacts")
    fun getContacts(): Flowable<List<User>>
}