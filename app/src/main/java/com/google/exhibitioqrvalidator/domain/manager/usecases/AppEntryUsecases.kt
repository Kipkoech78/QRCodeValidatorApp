package com.google.exhibitioqrvalidator.domain.manager.usecases

data class AppEntryUseCases(
    val ReadAppEntry: ReadAppEntry,
    val SaveAppEntry: SaveAppEntry,
    val saveToken: saveToken,
    val ClearToken: ClearToken,
    val SaveUserLogin:SaveUserLogin,
    val ReadToken: ReadToken,
    val readUserLogin: readUserLogin
)

