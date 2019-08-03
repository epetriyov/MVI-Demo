package com.connect.android.client.model.profile

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: Me): Completable

    @Delete
    fun deleteUser(user: Me): Completable

    @Query("SELECT * FROM user ")
    fun getUser(): Flowable<List<Me>>
}