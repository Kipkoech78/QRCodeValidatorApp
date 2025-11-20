package com.google.exhibitioqrvalidator.data.remote

import com.google.exhibitioqrvalidator.domain.models.AttendanceResponse
import com.google.exhibitioqrvalidator.domain.models.LoginResponse
import com.google.exhibitioqrvalidator.domain.models.user
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface exhibitionAPi {
    @POST("auth/login")
    suspend fun LoginUser(@Body user: user): LoginResponse

    @GET("event/attendance/{qrId}")
    suspend fun MarkAttendance(@Path("qrId") qrId: String ): AttendanceResponse

}