package com.hema.e_commerce.ui.settings.auth.login

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.CustomersModel
import com.hema.e_commerce.model.remote.RetrofitInstance.Companion.api
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.LoginErrors
import com.hema.e_commerce.util.RepoErrors
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.launch


class LoginViewModel(application: Application, val AuthRepo: AuthRepo) :
    AndroidViewModel(application) {

    val loginSuccess: MutableLiveData<Boolean?> = MutableLiveData()
    val address: MutableLiveData<Boolean?> = MutableLiveData()


    @RequiresApi(Build.VERSION_CODES.M)
    fun getData(email: String,pass:String) {
        viewModelScope.launch {
            val response: Either<CustomersModel, LoginErrors> = AuthRepo.signIn(email,pass)

            when (response) {
                is Either.Error -> when (response.errorCode) {
                    LoginErrors.NoInternetConnection -> {
                        Toast.makeText(
                            getApplication(),
                            "NoInternetConnection" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.ServerError -> {

                        Toast.makeText(
                            getApplication(),
                            "ServerError" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.IncorrectEmailOrPassword->{
                        Toast.makeText(
                            getApplication(),
                            "CustomerNotFound" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.CustomerNotFound -> {
                        Toast.makeText(
                            getApplication(),
                            "CustomerNotFound" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Either.Success -> {
                    Log.d("singin", "" + response.data)
                    loginSuccess.postValue(true)
                }
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    fun address(id: Long, address: AddressModel) {
//        viewModelScope.launch {
//
//            when (val response: Either<AddressModel, RepoErrors> = AuthRepo.postAddress(id, address)) {
//                is Either.Error -> when (response.errorCode) {
//                    RepoErrors.NoInternetConnection -> {
//                        Toast.makeText(
//                            getApplication(),
//                            "NoInternet Connection" + response.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    RepoErrors.ServerError -> {
//
//                        Toast.makeText(
//                            getApplication(),
//                            "Server Error" + response.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//                is Either.Success -> {
//                    Log.d("address", "" + response.data)
//                    this@LoginViewModel.address.postValue(true)
//                }
//            }
//        }
//    }

    class Factory(private val application: Application, val AuthRepo: AuthRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application, AuthRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): LoginViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    AuthRepo(
                        api,
                        SharedPreferencesProvider(context.context?.applicationContext as Application),

                        context.context?.applicationContext as Application
                    )
                )
            )[LoginViewModel::class.java]
        }
    }
}