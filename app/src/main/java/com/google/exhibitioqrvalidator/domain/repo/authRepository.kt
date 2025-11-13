package com.google.exhibitioqrvalidator.domain.repo

import com.google.exhibitioqrvalidator.data.remote.exhibitionAPi
import com.google.exhibitioqrvalidator.domain.models.LoginResponse
import com.google.exhibitioqrvalidator.domain.models.user

class authRepository(private val exhibitionAPi: exhibitionAPi) {
    suspend fun loginUser(email: String, password: String): LoginResponse {
        return exhibitionAPi.LoginUser(user(email, password))
    }
}