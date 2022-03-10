package com.hema.e_commerce.ui.settings.address

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.Address
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.SharedPreferencesProvider


class AddAddressViewModel(application: Application, val productRepo: AuthRepo) : AndroidViewModel(application) {

    var addressSource = Address()
    val addressLiveData = MutableLiveData(addressSource)

    var isDefault = false

    var isValid = false

    var setErrors = false

    fun setErrors(){
        setErrors = true
        addressLiveData.value = addressSource
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun addAddress() = productRepo.addAddress(AddressModel(addressSource),isDefault)


    class Factory(private val application: Application,val productRepo: AuthRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddAddressViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment): AddAddressViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    AuthRepo(RetrofitInstance.api, SharedPreferencesProvider.getInstance(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[AddAddressViewModel::class.java]
        }
    }
}