package com.hema.e_commerce.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.hema.e_commerce.model.dataclass.customer.AddressesItem
import com.hema.e_commerce.model.dataclass.customer.Customer

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
        private const val SAVED_ADDRESS_ORDER = "ORDERED_ADRESS"

        fun getInstance(application: Application):SharedPreferencesProvider{
            return instance ?:synchronized(Lock){
                instance ?: SharedPreferencesProvider(application).also {
                    instance = it
                }
            }
        }

    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }
    fun saveAddress(address:AddressesItem)
    {
        val gson = Gson()
        val json = gson.toJson(address)
        editor.putString(SAVED_ADDRESS_ORDER, json)
        editor.commit()
    }
    fun getAddress(): AddressesItem? {
        val gson = Gson()
        val json: String? = pref.getString(SAVED_ADDRESS_ORDER, null)
        if (json != null){
        val obj: AddressesItem? = gson.fromJson(json, AddressesItem::class.java)
        return obj}
        return null
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
        get() = pref.getBoolean(IS_SIGN_IN, false)


    private fun toJson(settings: CustomerInfo): String {
        val json = Gson()
        return json.toJson(settings)
    }

    private fun fromJson(settings: String): CustomerInfo {
        val json = Gson()
        return json.fromJson(settings, CustomerInfo::class.java)
    }

    fun update(update: (CustomerInfo) -> CustomerInfo) {
        preferences.edit {
            putString(
                Constant.ALL_DATA,
                toJson(update(settings.value ?: CustomerInfo.getDefault()))
            )
            apply()
        }
    }

    fun getUserInfo(): CustomerInfo {
        return preferences.getString(Constant.ALL_DATA, null)?.let { fromJson(it) }
            ?: CustomerInfo.getDefault()
    }

}

data class CustomerInfo(var customer: Customer?) {
    companion object {
        fun getDefault(): CustomerInfo = CustomerInfo(null)
    }

}
