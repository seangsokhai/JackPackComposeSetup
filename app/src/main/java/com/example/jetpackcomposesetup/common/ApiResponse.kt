package com.example.jetpackcomposesetup.common


import com.squareup.moshi.JsonClass
import retrofit2.Response


@JsonClass(generateAdapter = true)
data class ApiResponse<T>(val success: Boolean, val data: T?, val statusCode: Int?, val message: String?) {
	companion object {
		fun <T> error(message: String?, statusCode: Int? = null): ApiResponse<T> {
			return ApiResponse(success = false, message = message, statusCode = statusCode, data = null)
		}
		
		fun <T> success(data: T): ApiResponse<T> {
			return ApiResponse(data = data, success = true, message = null, statusCode = null)
		}
	}
	
	inline fun onSuccess(action: (T) -> Unit): ApiResponse<T> {
		if (success) action(data as T)
		return this
	}
	
	inline fun onError(action: (String?) -> Unit): ApiResponse<T> {
		if (!success) action(message)
		return this
	}
	
	inline fun onDone(action: () -> Unit) {
		action()
	}
}

inline fun <T> runForResponse(block: () -> T): ApiResponse<T> {
	return try {
		ApiResponse.success(block())
	} catch (e: Throwable) {
		e.printStackTrace()
		ApiResponse.error(e.message)
	}
}