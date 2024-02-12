package com.example.homework_22.data.common

import android.util.Log.e
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResource {
    fun <T> handleResource(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Loader(loader = true))
            val data = call()
            if (data.isSuccessful) {
                emit(Resource.Success(success = data.body()!!))
            } else {
                emit(Resource.Error(error = data.errorBody().toString()))
            }
        } catch (e: Throwable) {
            e("omiko", e.message ?: "error")
        } finally {
            emit(Resource.Loader(loader = false))
        }
    }
}