package com.connect.android.client.model.contacts

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(items: List<User>): Completable

    @Delete
    fun deleteContacts(items: List<User>): Completable

    @Query("SELECT * FROM user")
    fun getAllContacts(): List<User>

    @RawQuery(observedEntities = [User::class])
    fun getContacts(query: SupportSQLiteQuery): Flowable<List<User>>
}