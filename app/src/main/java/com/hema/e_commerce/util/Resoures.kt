package com.hema.e_commerce.util

sealed class Either<S, E> {

    data class Success<S, E>(val data: S) : Either<S, E>()
    data class Error<S, E>(val errorCode: E, val message: String? = null) : Either<S, E>()

}

enum class RepoErrors {
    NoInternetConnection,
    ServerError,
    EmptyBody
}


enum class LoginErrors {
    NoInternetConnection,
    ServerError,
    CustomerNotFound,
    IncorrectEmailOrPassword,
    AddressError
}