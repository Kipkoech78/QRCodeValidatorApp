package com.google.exhibitioqrvalidator.domain.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserResponse
)

data class UserResponse(
    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("userName")
    val userName: String
)
