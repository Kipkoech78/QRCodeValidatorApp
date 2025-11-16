package com.google.exhibitioqrvalidator.domain.manager.usecases

import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localUserManager: LocalUserManager) {
    operator fun invoke(): Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}
class SaveAppEntry(private val localUserManager: LocalUserManager){
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}
class saveToken(private val localUserManager: LocalUserManager){
    suspend operator fun  invoke(token: String){
        localUserManager.saveToken(token)
    }
}
class ClearToken(private val localUserManager: LocalUserManager){
    suspend operator fun invoke(){
        localUserManager.clearToken()
    }
}

class SaveUserLogin(private val localUserManager: LocalUserManager){
    suspend operator fun invoke(){
        localUserManager.saveUserLogin()
    }
}
class ReadToken(private val localUserManager: LocalUserManager){
    operator fun invoke(): Flow<String?>{
        return localUserManager.readToken()
    }
}

class readUserLogin(private val localUserManager: LocalUserManager){
    operator fun invoke():Flow<Boolean>{
        return localUserManager.readUserLogin()
    }
}
