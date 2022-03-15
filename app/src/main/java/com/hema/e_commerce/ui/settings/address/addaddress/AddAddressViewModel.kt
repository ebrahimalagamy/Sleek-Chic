package com.hema.e_commerce.ui.settings.address.addaddress

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.RepoErrors
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.launch


class AddAddressViewModel(application: Application, val authRepo: AuthRepo) :
    AndroidViewModel(application) {

    val add: MutableLiveData<Boolean?> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun postData(address: AddressModel) {
        viewModelScope.launch {
            when (val response: Either<AddressModel, RepoErrors> = authRepo.addAddress(address)) {
                is Either.Error -> when (response.errorCode) {
                    RepoErrors.ConnectionFiled -> {
                        Toast.makeText(
                            getApplication(),
                            "NoInternetConnection" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    RepoErrors.ServerError -> {

                        Toast.makeText(
                            getApplication(),
                            "ServerError" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Either.Success -> add.postValue(true)
            }
        }
    }


//    var addressLine = MutableLiveData<String>()
//    var city = MutableLiveData<String>()
//    val firstName = MutableLiveData<String>()
//    val lastName = MutableLiveData<String>()
//    val phone = MutableLiveData<String>()
//    val isDefult = MutableLiveData(false)
//    var addressAdded = MutableLiveData(false)
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun addAddress() {
//        if (Validate()) {
//            viewModelScope.launch {
//                when (val response: Either<AddressModel, LoginErrors> = authRepo.addAddress(
//                    AddressModel(
//                        Address(
//                            Random.nextLong(1, 1000000),
//                            addressLine.value,
//                            city.value,
//                            firstName.value,
//                            lastName.value,
//                            "000", isDefult.value!!, phone.value
//                        )
//                    )
//                )) {
//                    is Either.Error -> when (response.errorCode) {
//                        LoginErrors.NoInternetConnection -> {
//                            Toast.makeText(
//                                getApplication(), "NoInternet Connection" + response.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        LoginErrors.ServerError -> {
//                            Toast.makeText(
//                                getApplication(), "Server Error" + response.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        LoginErrors.AddressError -> {
//                            Toast.makeText(
//                                getApplication(), "address Error" + response.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                    is Either.Success -> {
//                        Log.d("ADD_ADDRESS", "address added")
//                        addressAdded.postValue(true)
//                    }
//
//                }
//            }
//        }
//    }
//
//    private fun Validate(): Boolean {
//        when {
//            city.value.isNullOrBlank() -> {
//                Toast.makeText(getApplication(), "please insert city", Toast.LENGTH_LONG).show()
//                return false
//            }
//            addressLine.value.isNullOrBlank() -> {
//                Toast.makeText(getApplication(), "please insert address line", Toast.LENGTH_LONG)
//                    .show()
//                return false
//            }
//            firstName.value.isNullOrBlank() -> {
//                Toast.makeText(getApplication(), "please insert first name", Toast.LENGTH_LONG)
//                    .show()
//                return false
//            }
//            lastName.value.isNullOrBlank() -> {
//                Toast.makeText(getApplication(), "please insert last name", Toast.LENGTH_LONG)
//                    .show()
//                return false
//            }
//            phone.value.isNullOrBlank() -> {
//                Toast.makeText(getApplication(), "please insert phone", Toast.LENGTH_LONG).show()
//                return false
//            }
//            else -> return true
//        }
//    }

    class Factory(private val application: Application, val productRepo: AuthRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddAddressViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): AddAddressViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    AuthRepo(
                        RetrofitInstance.api,
                        SharedPreferencesProvider.getInstance(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[AddAddressViewModel::class.java]
        }
    }
}