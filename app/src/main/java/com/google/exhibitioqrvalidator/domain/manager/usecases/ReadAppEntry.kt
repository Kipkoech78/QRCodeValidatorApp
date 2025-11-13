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