package com.hema.e_commerce.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.hema.e_commerce.util.Constant.LOCATION_REQUEST_CODE

@SuppressLint("StaticFieldLeak")
object LocationProvider {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationContext: Context
    private lateinit var context: Context
    private lateinit var sharedPref: SharedPreferencesProvider


    fun initLocation(context: Context) {
        locationContext = context.applicationContext
        this.context = context
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(locationContext)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        sharedPref = SharedPreferencesProvider(context)
    }

    fun getDeviceLocation() {

        if (ActivityCompat.checkSelfPermission(
                locationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                locationContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                LOCATION_REQUEST_CODE
            )
            return
        } else {
            mFusedLocationClient.lastLocation.addOnSuccessListener { it ->
                if (it != null) {
                    if (sharedPref.isFirstTimeLaunch) {
                        val geoCoder = Geocoder(context)
                        val currentLocation =
                            geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                        Log.d("first", currentLocation[0].getAddressLine(0))
                        sharedPref.setLocation(
                            it.latitude.toString(),
                            it.longitude.toString(),
                            currentLocation[0].getAddressLine(0),
                        )

//                        sharedPref.update {
//                            it.copy(customer = it.customer.lastName)
//                        }
                        // to get my location in first time only
                        sharedPref.setFirstTimeLaunch(false)

                    }
                } else {
                    Toast.makeText(
                        context,
                        "please make your device choose location",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation()

            }
        }
    }


}