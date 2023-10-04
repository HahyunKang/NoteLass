package com.app.note_lass.common

sealed class Resource<T>(val data: T? = null, val code: Int? = null, val message: String? = null) {

    class Success<T>(data: T?, code: Int?, message: String?)
        : Resource<T>(data = data, code = code, message = message)

    class Error<T>( message: String?, data: T? = null)
        : Resource<T>(message= message, data = data)

    class Loading<T>(data: T? = null): Resource<T>(data)
}
