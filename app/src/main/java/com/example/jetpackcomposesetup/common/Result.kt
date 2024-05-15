package com.example.jetpackcomposesetup.common


import com.example.jetpackcomposesetup.data.model.ApiError
import com.example.jetpackcomposesetup.fromJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.Response


sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}

@Suppress("UNCHECKED_CAST")
fun <T> Flow<Response<T>>.asResponseResult(): Flow<Result<T>> {
    return this
        .map<Response<T>, Result<T>> {
            if (it.isSuccessful) {
                Result.Success(it.body() as T)
            } else {
                val errorJson = it.errorBody()?.string()
                val error = errorJson?.fromJson(ApiError::class.java)
                throw Throwable(error?.message)
            }
        }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}
