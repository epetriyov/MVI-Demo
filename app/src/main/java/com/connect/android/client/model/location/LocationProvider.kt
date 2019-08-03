package com.connect.android.client.model.location

import android.annotation.SuppressLint
import android.location.Location
import com.patloew.rxlocation.RxLocation
import io.reactivex.Maybe

interface LocationProvider {

    fun provideLastLocation(): Maybe<Location>
}

class LocationProviderImpl(private val rxLocation: RxLocation) : LocationProvider {
    @SuppressLint("MissingPermission")
    override fun provideLastLocation(): Maybe<Location> {
        return rxLocation.location().lastLocation()
    }

}