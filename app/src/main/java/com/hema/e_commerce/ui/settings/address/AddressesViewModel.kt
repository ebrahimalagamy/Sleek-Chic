package com.hema.e_commerce.ui.settings.address

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.AddressArray
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.LoginErrors
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddressesViewModel(application: Application, val AuthRepo: AuthRepo) :
    AndroidViewModel(application) {
    var list: MutableLiveData<AddressArray> = MutableLiveData()
    val pref: SharedPreferencesProvider = SharedPreferencesProvider(application.applicationContext)

    @RequiresApi(Build.VERSION_CODES.M)
    fun delete(id: Long) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (val response: Either<AddressModel, LoginErrors> = AuthRepo.removeAddress(id)
            ) {
                is Either.Error -> when (response.errorCode) {
                    LoginErrors.ConnectionFiled -> {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(
                                getApplication(),
                                "NoInternet Connection" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    LoginErrors.ServerError -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                getApplication(),
                                "Server Error" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    LoginErrors.AddressError -> {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(
                                getApplication(),
                                "address Error" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                is Either.Success -> {
                    Log.d("ADDRESSES", "address deleted")


                }

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getAdressesList() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (val response: Either<AddressArray, LoginErrors> = AuthRepo.getAddress()
            ) {
                is Either.Error -> when (response.errorCode) {
                    LoginErrors.ConnectionFiled -> {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(
                                getApplication(),
                                "NoInternet Connection" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    LoginErrors.ServerError -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                getApplication(),
                                "Server Error" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    LoginErrors.AddressError -> {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(
                                getApplication(),
                                "address Error" + response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                is Either.Success -> {
                    Log.d("ADDRESSES", "list of addresses have been received")
                    list.postValue(response.data!!)
                    if (pref.getAddress() == null)
                        if (response.data.addresses!!.isNotEmpty()) {
                            pref.saveAddress(response.data.addresses[0]!!)
                        }

                }

            }

        }
        runBlocking {
            job.join()
        }
    }

    class Factory(private val application: Application, val AuthRepo: AuthRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddressesViewModel(application, AuthRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): AddressesViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    AuthRepo(
                        RetrofitInstance.api,
                        SharedPreferencesProvider(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[AddressesViewModel::class.java]
        }
    }
}