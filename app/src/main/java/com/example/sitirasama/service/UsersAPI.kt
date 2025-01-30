package com.example.sitirasama.service

import android.provider.ContactsContract
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.DELETE
import retrofit2.http.Path

interface UsersAPI {
    @POST("api/auth/register")
    fun createUser(@Body request: UserRequest) : Call<UserResponse>

    @POST("api/auth/login")
    fun loginUser(@Body request: UserRequest) : Call<UserResponse>

    @GET("api/auth/profil")
    fun getProfil(@Body request: UserRequest) : Call<UserResponse>

    @PATCH("api/auth/profil")
    fun patchProfil(@Body request: UserRequest) : Call<String>

    @PATCH("api/auth/gantiPassword")
    fun patchPassword(@Body request: UserRequest) : Call<String>

    @DELETE("api/auth/akun/{email}")
    fun deleteUser(@Path ("email") email: String) : Call<String>

    @POST("api/auth/barang")
    fun createPengajuan(@Body request: UserRequest) : Call<UserResponse>

    @GET("api/auth/pengajuan")
    fun getPengajuan(@Body request: UserRequest) : Call<UserResponse>

    @GET("api/auth/barang")
    fun getBarang(@Body request: UserRequest) : Call<UserResponse>

    @GET("api/auth/barang/tolak")
    fun getBarangDitolak(@Body request: UserRequest) : Call<UserResponse>

    @DELETE("api/auth/pengajuan/{id}")
    fun deletePengajuan(@Path ("id") id: Int) : Call<String>

    @PATCH("api/auth/pengajuan")
    fun patchPengajuan(@Body request: UserRequest) : Call<UserResponse>

    @PATCH("api/auth/barang/tolak")
    fun patchBarangDitolak(@Body request: UserRequest) : Call<UserResponse>

    @PATCH("api/auth/barang/{id}")
    fun patchBarang(@Body request: UserRequest) : Call<UserResponse>

    @DELETE("api/auth/barang/{id}")
    fun deleteBarang(@Path ("id") id: Int) : Call<String>

    @DELETE("api/auth/barang/tolak/{id}")
    fun deleteBarangDitolak(@Path ("id") id: Int) : Call<String>
}