package com.hema.e_commerce.util

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
        private const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"

        // user Info
        private const val USER_ADDRESS = "USER_ADDRESS"
        private const val PHONE = "PHONE"
        private const val NAME = "NAME"

        // Shared preferences for location
        private const val IS_LOCATION_ENABLED = "IS_LOCATION_ENABLED"

    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun setLocation(latitude: String?, longitude: String?, address: String?) {
        editor.putString(LAT_SHARED_PREF, latitude)
        editor.putString(LONG_SHARED_PREF, longitude)
        editor.putString(ADDRESS, address)
        editor.commit()
    }



    val getLocation: Array<String?>
        get() {
            val location = arrayOfNulls<String>(3)
            val lat = pref.getString(LAT_SHARED_PREF, "null")
            val lng = pref.getString(LONG_SHARED_PREF, "null")
            val add = pref.getString(ADDRESS, "Address")
            location[0] = lat
            location[1] = lng
            location[2] = add
            return location
        }

    fun setUserInfo(//userAddress: String?,
                    phone: String?, name: String?) {
//        editor.putString(USER_ADDRESS, userAddress)
        editor.putString(PHONE, phone)
        editor.putString(NAME, name)
        editor.commit()
    }

    val getUserInfo: Array<String?>
        get() {
            val info = arrayOfNulls<String>(2)
//            val userAddress = pref.getString(USER_ADDRESS, null)
            val phone = pref.getString(PHONE,"Phone")
            val name = pref.getString(NAME, "Username")
//            info[0] = userAddress
            info[0] = phone
            info[1] = name
            return info
        }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }
    val isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)

    fun setFirstTimeLocationenabled(isFirstTime: Boolean) {
        editor.putBoolean(IS_LOCATION_ENABLED, isFirstTime)
        editor.commit()
    }
}