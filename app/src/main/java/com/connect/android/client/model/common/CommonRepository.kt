package com.connect.android.client.model.common

import io.reactivex.Maybe
import io.reactivex.Single

interface CommonRepository {

    fun getGoals(): Maybe<List<String>>

    fun getTags(tagName: String): Single<Map<String, ArrayList<String>>>

    fun isFirstRun(): Boolean

    fun setFirstRun()

    fun resetFirstRun()
}

class CommonRepoImpl(private val commonApi: CommonApi, private val commonStore: CommonStore): CommonRepository
{
    override fun getGoals(): Maybe<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTags(tagName: String): Single<Map<String, ArrayList<String>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFirstRun(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFirstRun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetFirstRun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}