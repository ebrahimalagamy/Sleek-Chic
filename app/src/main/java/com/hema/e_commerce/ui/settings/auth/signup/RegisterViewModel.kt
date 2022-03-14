package com.hema.e_commerce.ui.settings.auth.signup

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.RepoErrors
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application, private val authRepo: AuthRepo) :
    AndroidViewModel(application) {

    val signupSuccess: MutableLiveData<Boolean?> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun postData(customer: CustomerModel) {
        viewModelScope.launch {
            when (val response: Either<CustomerModel, RepoErrors> = authRepo.signUp(customer)) {
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
                is Either.Success -> signupSuccess.postValue(true)
            }
        }
    }



    class Factory(
        private val application: Application,
        val authRepo: AuthRepo
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(application, authRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): RegisterViewModel {
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
            )[RegisterViewModel::class.java]
        }
    }

}