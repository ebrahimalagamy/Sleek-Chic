package com.hema.e_commerce.model.repository

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hema.e_commerce.model.dataclass.customer.AddressArray
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.dataclass.customer.CustomersModel
import com.hema.e_commerce.model.dataclass.test.Order
import com.hema.e_commerce.model.dataclass.test.OrderResponce
import com.hema.e_commerce.model.remote.RetrofitInstance.Companion.api
import com.hema.e_commerce.model.remote.ShopifyApi
import com.hema.e_commerce.util.*

class AuthRepo(
    val shopifyApi: ShopifyApi,
    var sharedPref: SharedPreferencesProvider,
    val application: Application
) {

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun signUp(customer: CustomerModel): Either<CustomerModel, RepoErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.register(customer)
                if (res.isSuccessful) {
                    Log.d("body", res.body()?.customer.toString())
                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError, res.message())
            } else
                Either.Error(RepoErrors.ConnectionFiled, "Connection Filed")

        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun signIn(email: String, pass: String): Either<CustomersModel, LoginErrors> {
        return try {
            if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.login()
                if (res.isSuccessful) {

                    val customer = res.body()?.customer?.first {
                        it?.email.equals(email)
                    } ?: return Either.Error(LoginErrors.UserNotFound, "User Not Found")
                    if (customer.lastName.equals(pass)) {
                        sharedPref.update {
                            it.copy(
                                customer = customer
                            )
                        }
                    } else return Either.Error(
                        LoginErrors.IncorrectPassword,
                        "Please Insert Correct email or password"
                    )

                    return Either.Success(res.body()!!)
                } else
                    return Either.Error(LoginErrors.ServerError, res.message())
            } else
                return Either.Error(LoginErrors.ConnectionFiled, "Connection Filed")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun updateCustomer(
        id: Long,
        customer: CustomerModel
    ): Either<CustomerModel, LoginErrors> {
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
                Either.Error(LoginErrors.ConnectionFiled, "Connection Filed")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun updateAddress(address: AddressModel): Either<AddressModel, LoginErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.updateAddress(
                    sharedPref.getUserInfo().customer?.customerId!!,
                    address.address.id!!,
                    address
                )
                if (res.isSuccessful) {

                    Log.d("body", res.body()?.address.toString())

                    Either.Success(res.body()!!)
                } else
                    Either.Error(LoginErrors.ServerError, res.message())
            } else
                Either.Error(LoginErrors.ConnectionFiled, "Connection Filed")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun addAddress(address: AddressModel): Either<AddressModel, RepoErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val customerId = sharedPref.getUserInfo().customer?.customerId
                val res = api.addAddress(customerId!!, address)
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError, res.message())
            } else
                Either.Error(RepoErrors.ConnectionFiled, "Connection Filed")
        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun removeAddress(id: Long): Either<AddressModel, LoginErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.deleteAddress(sharedPref.getUserInfo().customer?.customerId!!, id)
                Log.d("address id", res.toString())
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else
                    Either.Error(LoginErrors.ServerError, res.message())
            } else
                Either.Error(LoginErrors.ConnectionFiled, "Connection Filed")
        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getAddress(): Either<AddressArray, LoginErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.getAddress(sharedPref.getUserInfo().customer?.customerId!!)
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else
                    Either.Error(LoginErrors.ServerError, res.message())
            } else
                Either.Error(LoginErrors.ConnectionFiled, "Connection Filed")
        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError, t.message)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun createOrder(order: Order): Either<OrderResponce, RepoErrors> {
        return try {
            return if (Connectivity.isOnline(application.applicationContext)) {
                val res = api.createOrder(order)
                if (res.isSuccessful) {
                    Log.d("body", res.body()?.order.toString())
                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError, res.message())
            } else
                Either.Error(RepoErrors.ConnectionFiled, "Connection Field")

        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

}