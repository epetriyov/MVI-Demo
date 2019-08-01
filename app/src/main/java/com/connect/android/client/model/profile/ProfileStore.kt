package com.connect.android.client.model.profile

//import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Maybe

interface ProfileStore {

    fun loadCurrentProfile(): Maybe<User>

    fun saveCurrentProfile(user: User)

    fun removeProfile()
}

class SharedProfileStore(
//    private val rxSharedPreferences: RxSharedPreferences
): ProfileStore{
    override fun loadCurrentProfile(): Maybe<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveCurrentProfile(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeProfile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}