package com.example.sitirasama.service

import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UsersAPI {

    // 🔹 Endpoint untuk Autentikasi
    @POST("api/auth/register")
    fun createUser(@Body request: UserRequest): Call<ResponseBody>

    @POST("api/auth/login")
    fun loginUser(@Body request: UserRequest): Call<UserResponse>

    @GET("api/auth/profil")
    fun getProfil(): Call<UserResponse>

    @PATCH("api/auth/profil")
    fun updateProfil(@Body request: UserRequest): Call<ResponseBody>

    @PATCH("api/auth/gantiPassword")
    fun updatePassword(@Body request: UserRequest): Call<ResponseBody>

    @DELETE("api/auth/akun/{email}")
    fun deleteUser(@Path("email") email: String): Call<ResponseBody>

    // 🔹 Endpoint untuk Pengajuan Barang
    @POST("api/auth/barang")
    fun createPengajuan(@Body request: UserRequest): Call<ResponseBody>

    @GET("api/auth/pengajuan")
    fun getPengajuan(): Call<List<UserResponse>>

    @PATCH("api/auth/pengajuan")
    fun patchPengajuan(@Body request: UserRequest): Call<ResponseBody>

    @DELETE("api/auth/pengajuan/{id}")
    fun deletePengajuan(@Path("id") id: Int): Call<ResponseBody>

    // 🔹 Endpoint untuk Penitipan Barang
    @GET("api/auth/barang")
    fun getBarang(): Call<List<UserResponse>>

    @PATCH("api/auth/barang/{id}")
    fun patchBarang(@Path("id") id: Int, @Body request: UserRequest): Call<ResponseBody>

    @DELETE("api/auth/barang/{id}")
    fun deleteBarang(@Path("id") id: Int): Call<ResponseBody>

    // 🔹 Endpoint untuk Barang Ditolak
    @GET("api/auth/barang/tolak")
    fun getBarangDitolak(): Call<List<UserResponse>>

    @PATCH("api/auth/barang/tolak")
    fun patchBarangDitolak(@Body request: UserRequest): Call<ResponseBody>

    @DELETE("api/auth/barang/tolak/{id}")
    fun deleteBarangDitolak(@Path("id") id: Int): Call<ResponseBody>
}