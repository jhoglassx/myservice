package com.jhoglas.mysalon.ui.auth

import com.google.gson.annotations.SerializedName

data class UserData(
    val name: String? = null,
    val email: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("date_create")
    val dateCreate: String? = null,
    @SerializedName("date_update")
    val dateUpdate: String? = null,
    val image: String? = null,
)
