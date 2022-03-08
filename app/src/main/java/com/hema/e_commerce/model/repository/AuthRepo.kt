package com.hema.e_commerce.model.repository

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hema.e_commerce.model.dataclass.customer.CustomerLoginModel
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.remote.RetrofitInstance.Companion.api
import com.hema.e_commerce.model.remote.ShopifyApi
import com.hema.e_commerce.util.*

class AuthRepo(
    val ShopifyServices: ShopifyApi,
    var sharedPref: SharedPreferencesProvider,
    val application: Application
) {


    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun signUp(customer: CustomerModel): Either<CustomerModel, RepoErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.register(customer)
                if (res.isSuccessful) {
                    sharedPref.update {
                        it.copy(
                            customer = res.body()?.customer
                        )
                    }

                    Log.d("body", res.body()?.customer.toString())

                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError, res.message())
            } else
                Either.Error(RepoErrors.NoInternetConnection, "NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun signIn(email: String): Either<CustomerLoginModel, LoginErrors> {
        return try {
            if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.login()
                if (res.isSuccessful) {

                    val customer = res.body()?.customer?.first {
                        it?.email.equals(email)
                    } ?: return Either.Error(LoginErrors.CustomerNotFound, "CustomerNotFound")

                    sharedPref.update {
                        it.copy(
                            customer = customer
                        )
                    }

                    return Either.Success(res.body()!!)
                } else
                    return Either.Error(LoginErrors.ServerError, res.message())
            } else
                return Either.Error(LoginErrors.NoInternetConnection, "NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun updateCustomer(customer_id: Long) {

        if (Connectivity.isOnline(application.applicationContext)) {
            val res = api.updateCustomer(customer_id)
            if (res.isSuccessful) {

                val customer = res.body()?.customer?.customerId
                Log.d("dd0",customer.toString())


//                    sharedPref.update {
//                        it.copy(
//                            customer = customer
//                        )
//                    }
            }
        }
    }


}