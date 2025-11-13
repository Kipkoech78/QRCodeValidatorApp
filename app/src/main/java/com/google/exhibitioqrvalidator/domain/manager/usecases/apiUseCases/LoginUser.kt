package com.google.exhibitioqrvalidator.domain.manager.usecases.apiUseCases

import com.google.exhibitioqrvalidator.domain.models.LoginResponse
import com.google.exhibitioqrvalidator.domain.models.user
import com.google.exhibitioqrvalidator.domain.repo.authRepository

class loginUserasCase(private val loginRepository: authRepository) {
    suspend operator fun invoke(user: user): LoginResponse {
        return loginRepository.loginUser(user.email, user.password)
    }
}