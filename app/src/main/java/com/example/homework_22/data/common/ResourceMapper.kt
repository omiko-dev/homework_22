package com.example.homework_22.data.common

fun <T, R> Resource<T>.resourceMapper(mapper: (T) -> R): Resource<R> =
    when (this) {
        is Resource.Success -> Resource.Success(success = mapper(success))
        is Resource.Error -> Resource.Error(error = error)
        is Resource.Loader -> Resource.Loader(loader = loader)
    }