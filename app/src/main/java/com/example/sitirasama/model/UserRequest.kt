package com.example.sitirasama.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequest {
    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("oldPassword")
    @Expose
    var oldPassword: String? = null

    @SerializedName("newPassword")
    @Expose
    var newPassword: String? = null

    @SerializedName("barang")
    @Expose
    var barang: String? = null

    @SerializedName("deskripsi")
    @Expose
    var deskripsi: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("alasan")
    @Expose
    var alasan: String? = null

    @SerializedName("barcode")
    @Expose
    var barcode: String? = null

    @SerializedName("statuspenitipan")
    @Expose
    var statuspenitipan: String? = null

}