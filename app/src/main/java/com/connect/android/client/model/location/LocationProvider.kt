package com.connect.android.client.model.location

import android.location.Location
import com.patloew.rxlocation.RxLocation
import io.reactivex.Observable

interface LocationProvider {

    fun provideLastLocation(): Observable<Location>
}

class LocationProviderImpl(private val rxLocation: RxLocation) : LocationProvider {
    override fun provideLastLocation(): Observable<Location> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}