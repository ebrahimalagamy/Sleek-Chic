package com.hema.e_commerce.ui.settings.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesProvider(context: Context) {
    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private const val PREF_NAME = "myPref"
        // Shared preferences for lat,lon and address
        private const val LAT_SHARED_PREF = "LAT_SHARED_PREF"
        private const val LONG_SHARED_PREF = "LONG_SHARED_PREF"
        private const val ADDRESS = "ADDRESS"
        //location request
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun setLocation(latitude: String?, longitude: String?,address:String?) {
        editor.putString(LAT_SHARED_PREF, latitude)
        editor.putString(LONG_SHARED_PREF, longitude)
        editor.putString(ADDRESS, address)
        editor.commit()
    }

    val latLong: Array<String?>
        get() {
            val location = arrayOfNulls<String>(3)
            val lat = pref.getString(LAT_SHARED_PREF, null)
            val lng = pref.getString(LONG_SHARED_PREF, null)
            val add = pref.getString(ADDRESS, null)
            location[0] = lat
            location[1] = lng
            location[2] = add
            return location
        }
}