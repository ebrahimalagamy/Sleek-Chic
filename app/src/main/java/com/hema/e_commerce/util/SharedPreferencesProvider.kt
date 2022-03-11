package com.hema.e_commerce.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.hema.e_commerce.model.dataclass.customer.Customer
import java.util.concurrent.locks.Lock

class SharedPreferencesProvider(context: Context) {
    companion object {
        private var instance: SharedPreferencesProvider? = null
        private val Lock = Any()
        private lateinit var pref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private const val PREF_NAME = "myPref"

        // Shared preferences for lat,lon and address
        private const val LAT_SHARED_PREF = "LAT_SHA.0,RED_PREF"
        private const val LONG_SHARED_PREF = "LONG_SHARED_PREF"
        private const val ADDRESS = "ADDRESS"
        private const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"
        private const val IS_SIGN_IN = "IS_SIGN_IN"

        // user Info
        private const val PHONE = "PHONE"
        private const val NAME = "NAME"
        fun getInstance(application: Application):SharedPreferencesProvider{
            return instance ?:synchronized(Lock){
                instance ?: SharedPreferencesProvider(application).also {
                    instance = it
                }
            }
        }

        private const val USER_STATS="user-state"


    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    }

    val settings: MutableLiveData<CustomerInfo> by lazy {
        MutableLiveData<CustomerInfo>().apply {
            postValue(CustomerInfo.getDefault())
        }
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
        phone: String?, name: String?
    ) {
//        editor.putString(USER_ADDRESS, userAddress)
        editor.putString(PHONE, phone)
        editor.putString(NAME, name)
        editor.commit()
    }

    val getUserInfo: Array<String?>
        get() {
            val info = arrayOfNulls<String>(2)
            val phone = pref.getString(PHONE, "Phone")
            val name = pref.getString(NAME, "Username")
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

    fun checkSignIn(isSignIn: Boolean) {
        editor.putBoolean(IS_SIGN_IN, isSignIn)
        editor.commit()
    }

    val isSignIn: Boolean
        get() = pref.getBoolean(IS_SIGN_IN, true)


    private fun settingsToJson(settings: CustomerInfo): String {
        val json = Gson()
        return json.toJson(settings)
    }
    ///////////////
    fun getUserStatus(): Boolean {
        return pref!!.getBoolean(USER_STATS, false)
    }

    private fun settingsFromJson(settings: String): CustomerInfo {
        val json = Gson()
        return json.fromJson(settings, CustomerInfo::class.java)
    }

    fun update(update: (CustomerInfo) -> CustomerInfo) {
        preferences.edit {
            putString(
                Constant.ALL_DATA_ROUTE,
                settingsToJson(update(settings.value ?: CustomerInfo.getDefault()))
            )
            apply()
        }
    }

    fun getUserInfo(): CustomerInfo {
        return preferences.getString(Constant.ALL_DATA_ROUTE, null)?.let { settingsFromJson(it) }
            ?: CustomerInfo.getDefault()
    }
//    fun getUserAddress(): CustomerAddress {
//        return preferences.getString(Constant.ALL_DATA_ROUTE, null)?.let { settingsFromJson(it) }
//            ?: CustomerAddress.getDefault()
//    }
}

data class CustomerInfo(var customer: Customer?) {
    companion object {
        fun getDefault(): CustomerInfo = CustomerInfo(null)
    }

}
//
//data class CustomerAddress(var address: Address?) {
//    companion object {
//        fun getDefault(): CustomerAddress = CustomerAddress(null)
//    }
//}