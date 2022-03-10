package com.hema.e_commerce.model.repository

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hema.e_commerce.model.dataclass.customer.Address
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.dataclass.customer.CustomersModel
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
    suspend fun signIn(email: String): Either<CustomersModel, LoginErrors> {
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
    suspend fun update(id: Long, customer: CustomerModel): Either<CustomerModel, LoginErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.update(id, customer)
                if (res.isSuccessful) {
                    sharedPref.update {
                        it.copy(
                            customer = res.body()?.customer
                        )
                    }
                    Log.d("body", res.body()?.customer.toString())

                    Either.Success(res.body()!!)
                } else
                    Either.Error(LoginErrors.ServerError, res.message())
            } else
                Either.Error(LoginErrors.NoInternetConnection, "NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun postAddress(id: Long, address: AddressModel): Either<AddressModel, RepoErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.postAddress(id, address)
                if (res.isSuccessful) {
                    sharedPref.setLocation(
                        address = res.body()?.address?.address1,
                        latitude = res.body()?.address?.firstName,
                        longitude = res.body()?.address?.city

                    )
                    Log.d("body", res.body()?.address?.address1.toString())

                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError, res.message())
            } else
                Either.Error(RepoErrors.NoInternetConnection, "NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }


}