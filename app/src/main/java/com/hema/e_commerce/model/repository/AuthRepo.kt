package com.hema.e_commerce.model.repository

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.dataclass.customer.Customer
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.dataclass.customer.CustomersModel
import com.hema.e_commerce.model.remote.RetrofitInstance.Companion.api
import com.hema.e_commerce.model.remote.ShopifyApi
import com.hema.e_commerce.util.*
import retrofit2.Response

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
    suspend fun signIn(email: String,pass:String): Either<CustomersModel, LoginErrors> {
        return try {
            if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.login()
                if (res.isSuccessful) {

                    val customer = res.body()?.customer?.first {
                        it?.email.equals(email)
                    } ?: return Either.Error(LoginErrors.CustomerNotFound, "CustomerNotFound")
                    if (customer.lastName.equals(pass)) {
                        sharedPref.update {
                            it.copy(
                                customer = customer
                            )
                        }
                    } else return Either.Error(
                        LoginErrors.IncorrectEmailOrPassword,
                        "Please enter correct email or password"
                    )


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

//    @RequiresApi(Build.VERSION_CODES.M)
//    suspend fun postAddress(id: Long, address: AddressModel): Either<AddressModel, RepoErrors> {
//        return try {
//            return if (Connectivity.isOnline(application.applicationContext)) {
//                val res = api.postAddress(id, address)
//                if (res.isSuccessful) {
//                    sharedPref.setLocation(
//                        address = res.body()?.address?.address1,
//                        latitude = res.body()?.address?.firstName,
//                        longitude = res.body()?.address?.city
//
//                    )
//                    Log.d("body", res.body()?.address?.address1.toString())
//
//                    Either.Success(res.body()!!)
//                } else
//                    Either.Error(RepoErrors.ServerError, res.message())
//            } else
//                Either.Error(RepoErrors.NoInternetConnection, "NoInternetConnection")
//
//        } catch (t: Throwable) {
//            Either.Error(RepoErrors.ServerError, t.message)
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun addAddress(address: AddressModel, isDefault: Boolean): Either<Unit, RepoErrors> {
        val customerId = getCustomerFromSettings()?.customerId
        return if (customerId != null) {

            val res = callErrorsHandler(
                application,
                { ShopifyServices.addAddress(customerId, address) }) {
                Either.Success(it)
            }

            when (res) {
                is Either.Error -> Either.Error(res.errorCode, res.message)
                is Either.Success -> {
                    if (!isDefault)
                        Either.Success(Unit)
                    else {
                        val id = res.data.customerAddress?.id
                        if (id == null) {
                            Either.Error(
                                RepoErrors.ServerError,
                                "customerAddress ==null or id == null"
                            )
                        } else {
                            callErrorsHandler(
                                application,
                                { ShopifyServices.setDefault(customerId, id) }) {
                                Either.Success(Unit)
                            }
                        }
                    }

                }
            }

        } else
            Either.Error(RepoErrors.ServerError, "NoCustomer")
    }
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getAddress(): Either<Customer, RepoErrors> {

        val customerId = getCustomerFromSettings()?.customerId
        return if (customerId != null)
            callErrorsHandler(application, { ShopifyServices.getAddress(customerId) }) {
                if (it.customer != null)
                    Either.Success(it.customer)
                else
                    Either.Error(RepoErrors.EmptyBody)
            }
        else
            Either.Error(RepoErrors.ServerError, "NoCustomer")
    }

    //    suspend fun updateAddress(addressId: Long, address: AddressModel): Either<Unit, RepoErrors> {
//        val customerId = getCustomerFromSettings()?.customerId
//        return if (customerId != null)
//            callErrorsHandler(
//                application,
//                { shopifyServices.updateAddress(customerId, addressId, address) }) {
//                Either.Success(Unit)
//            }
//        else
//            Either.Error(RepoErrors.ServerError, "NoCustomer")
//    }
    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun <S, R> callErrorsHandler(
        application: Application, suspendedCall: suspend () -> Response<S>,
        noErrors: suspend ((S) -> Either<R, RepoErrors>)
    ): Either<R, RepoErrors> {
        if (Connectivity.isOnline(application)) {
            try {
                val response = suspendedCall()
                return if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("hemaaaaa", "" + data)

                    return if (data != null) {
                        noErrors(data)
                    } else {
                        Either.Error(RepoErrors.EmptyBody, "no Values was Found")
                    }
                } else {
                    Either.Error(RepoErrors.ServerError, response.message())
                }
            } catch (t: Throwable) {
                return Either.Error(RepoErrors.ServerError, t.message)
            }
        } else {
            return Either.Error(RepoErrors.NoInternetConnection, "no internet Connection")
        }
    }

    private fun getCustomerFromSettings() = sharedPref.getUserInfo().customer

}