package com.example.sitirasama.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Model data untuk Login
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val message: String, val accessToken: String)

// Interface Retrofit untuk API
interface APIService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    companion object {
        private const val BASE_URL = "http://172.16.100.51:8080/" //Diganti sesuai IP Address v4 komputer saat running API

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}