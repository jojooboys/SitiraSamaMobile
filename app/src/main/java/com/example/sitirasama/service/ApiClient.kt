package com.example.sitirasama.service

import android.content.Context
import com.example.sitirasama.util.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://172.16.100.51:8080/"

    private var sessionManager: SessionManager? = null

    fun init(context: Context) {
        sessionManager = SessionManager(context)
    }

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val token = sessionManager?.getToken() ?: ""

        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    val apiService: UsersAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UsersAPI::class.java)
    }
}
