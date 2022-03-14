package com.hema.e_commerce.ui.settings.auth.signin

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
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.launch


class SignInViewModel(application: Application, val AuthRepo: AuthRepo) :
    AndroidViewModel(application) {

    val mldSignIn: MutableLiveData<Boolean?> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun getData(email: String,pass:String) {
        viewModelScope.launch {

            when (val response: Either<CustomersModel, LoginErrors> = AuthRepo.signIn(email,pass)) {
                is Either.Error -> when (response.errorCode) {
                    LoginErrors.ConnectionFiled -> {
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
                    LoginErrors.IncorrectPassword->{
                        Toast.makeText(
                            getApplication(),
                            "IncorrectEmailOrPassword" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.UserNotFound -> {
                        Toast.makeText(
                            getApplication(),
                            "CustomerNotFound" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Either.Success -> {
                    Log.d("singin", "" + response.data)
                    mldSignIn.postValue(true)
                }
            }
        }
    }

    class Factory(private val application: Application, val AuthRepo: AuthRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(application, AuthRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): SignInViewModel {
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
            )[SignInViewModel::class.java]
        }
    }
}