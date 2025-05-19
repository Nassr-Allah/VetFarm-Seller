package com.nassrallah.vetfarmseller.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class LocationClientImpl(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Flow<Location> {
        return callbackFlow {
            if (!context.hasLocationPermission()) {
                throw Exception("Location Permission is not granted")
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnabled && !isNetworkEnabled) {
                throw Exception("GPS is disabled")
            }

            val currentLocationRequest = CurrentLocationRequest.Builder().setDurationMillis(10000L)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY).build()

            client.getCurrentLocation(currentLocationRequest, CancellationTokenSource().token).apply {
                addOnCompleteListener {
                    launch { send(it.result) }
                }
                addOnFailureListener {
                    throw it
                }
            }

            awaitClose {  }
        }
    }
}