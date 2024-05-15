package com.example.jetpackcomposesetup.di

import android.content.Context
import android.util.Log
import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.BuildConfig
import com.example.jetpackcomposesetup.MainApp
import com.example.jetpackcomposesetup.R
import com.example.jetpackcomposesetup.common.ConnectivityManagerNetworkMonitor
import com.example.jetpackcomposesetup.common.NetworkMonitor
import com.example.jetpackcomposesetup.data.api.AuthInterceptor
import com.example.jetpackcomposesetup.data.api.MainApi
import com.example.jetpackcomposesetup.data.repo.MainRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

	@Provides
	fun mainApp(@ApplicationContext context: Context): MainApp = context as MainApp

	@Provides
	fun baseUrl(@ApplicationContext context: Context, prefs: AppSharedPrefs): String = prefs.serverUrl ?: context.getString(
		R.string.api_base_url)

	@Provides
	fun prefs(@ApplicationContext context: Context): AppSharedPrefs =
		AppSharedPrefs.getInstance(context)

	@Singleton
	@Provides
	fun networkMonitor(@ApplicationContext context: Context): NetworkMonitor =
		ConnectivityManagerNetworkMonitor(context)

	@Provides
	fun moshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

	@Provides
	fun interceptor(prefs: AppSharedPrefs, moshi: Moshi): AuthInterceptor =
		AuthInterceptor(prefs, moshi)

	@Provides
	fun okHttp(authInterceptor: AuthInterceptor, prefs: AppSharedPrefs): OkHttpClient {
		val client = OkHttpClient.Builder()
			.readTimeout(30, TimeUnit.SECONDS)
			.connectTimeout(30, TimeUnit.SECONDS)
			.addInterceptor(authInterceptor)
			.addInterceptor {
				it.proceed(it.request()).also { res ->
					when (res.code) {
						900 -> {
							prefs.updateSessionToken(null)
							prefs.installationId = null
							Log.d(">>>", ">>> invalid session token! token cleared!")
						}

						650 -> {
							prefs.updateSessionToken(null)
							prefs.installationId = null
							Log.d(">>>", ">>> invalid installation id!")
						}
					}
				}
			}

        if (BuildConfig.DEBUG) client.addInterceptor(HttpLoggingInterceptor()
            .apply { setLevel(HttpLoggingInterceptor.Level.BODY) })

		return client.build()
	}

	@Provides
	fun retrofit(
		baseUrl: String,
		moshi: Moshi,
		okHttpClient: OkHttpClient
	): Retrofit = Retrofit.Builder()
		.baseUrl(baseUrl)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.addConverterFactory(ScalarsConverterFactory.create())
		.client(okHttpClient)
		.build()

	@Singleton
	@Provides
	fun mainApi(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)

	@Singleton
	@Provides
	fun mainRepo(api: MainApi, prefs: AppSharedPrefs) =
		MainRepo(api, prefs)

}