package com.google.exhibitioqrvalidator.data.remote

import com.google.exhibitioqrvalidator.domain.models.LoginResponse
import com.google.exhibitioqrvalidator.domain.models.user
import retrofit2.http.Body
import retrofit2.http.POST

interface exhibitionAPi {
    @POST("auth/login")
    suspend fun LoginUser(@Body user: user): LoginResponse
}