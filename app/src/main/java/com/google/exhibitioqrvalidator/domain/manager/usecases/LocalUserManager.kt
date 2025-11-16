package com.google.exhibitioqrvalidator.domain.manager.usecases

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
    suspend fun saveUserLogin()
    fun readUserLogin(): Flow<Boolean>
    suspend fun saveToken(token: String)
    fun readToken(): Flow<String>
    suspend fun clearToken()
//    suspend fun deleteAppEntry()

}

