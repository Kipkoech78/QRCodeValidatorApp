package com.google.exhibitioqrvalidator.domain.models

import com.google.gson.annotations.SerializedName

data class user(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)