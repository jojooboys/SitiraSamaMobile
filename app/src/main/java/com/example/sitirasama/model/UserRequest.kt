package com.example.sitirasama.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("oldPassword") val oldPassword: String? = null,
    @SerializedName("newPassword") val newPassword: String? = null,
    @SerializedName("barang") val barang: String? = null,
    @SerializedName("deskripsi") val deskripsi: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("alasan") var alasan: String? = null,
    @SerializedName("barcode") val barcode: String? = null,
    @SerializedName("statuspenitipan") val statuspenitipan: String? = null
)
