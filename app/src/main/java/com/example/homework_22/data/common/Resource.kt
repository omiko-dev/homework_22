package com.example.homework_22.data.common

sealed class Resource<out T> {
    class Success<out T>(val success: T): Resource<T>()
    class Error(val error: String): Resource<Nothing>()
    data class Loader(val loader: Boolean): Resource<Nothing>()
}