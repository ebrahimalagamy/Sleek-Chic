package com.hema.e_commerce.ui.settings.profile.editprofile

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.remote.RetrofitInstance.Companion.api
import com.hema.e_commerce.model.repository.AuthRepo
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.LoginErrors
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.launch


class EditProfileViewModel(application: Application, val AuthRepo: AuthRepo) :
    AndroidViewModel(application) {

    val updateUser: MutableLiveData<Boolean?> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun update(id: Long, customer: CustomerModel) {
        viewModelScope.launch {

            when (val response: Either<CustomerModel, LoginErrors> = AuthRepo.updateCustomer(id, customer)) {
                is Either.Error -> when (response.errorCode) {
                    LoginErrors.ConnectionFiled -> {
                        Toast.makeText(
                            getApplication(),
                            "NoInternet Connection" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.ServerError -> {

                        Toast.makeText(
                            getApplication(),
                            "Server Error" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginErrors.UserNotFound -> {
                        Toast.makeText(
                            getApplication(),
                            "Customer Not Found" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Either.Success -> {
                    Log.d("singin", "" + response.data)
                    updateUser.postValue(true)
                }
            }
        }
    }


    class Factory(private val application: Application, val AuthRepo: AuthRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditProfileViewModel(application, AuthRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): EditProfileViewModel {
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
            )[EditProfileViewModel::class.java]
        }
    }
}