package com.example.jetpackcomposesetup.data.api


import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.getServerLanguageCode
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
	private val prefs: AppSharedPrefs,
	private val moshi: Moshi
) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val requestBuilder = chain.request()
			.newBuilder()
			.header("Content-Type", "application/json")
			.header("Device-Type", "android")
			.header("Accept-Language", getServerLanguageCode(prefs.language ?: "km"))

		// TODO : When Login API available
		/*requestBuilder.removeHeader("X-Session-Token")
		prefs.sessionToken?.let { token ->
			requestBuilder.header("X-Session-Token", token)
		}*/

		// TODO : Store installation id of user
		/*requestBuilder.removeHeader("X-Installation-Id")
		prefs.installationId?.let { installationId ->
			requestBuilder.header("X-Installation-Id", installationId)
		}*/
		
		return chain.proceed(requestBuilder.build())
	}
}