package com.google.exhibitioqrvalidator.di

import android.app.Application
import com.google.exhibitioqrvalidator.data.manager.LocalUserManagerImplementation
import com.google.exhibitioqrvalidator.data.remote.exhibitionAPi
import com.google.exhibitioqrvalidator.domain.manager.usecases.AppEntryUseCases
import com.google.exhibitioqrvalidator.domain.manager.usecases.ClearToken
import com.google.exhibitioqrvalidator.domain.manager.usecases.LocalUserManager
import com.google.exhibitioqrvalidator.domain.manager.usecases.LoginUseCases
import com.google.exhibitioqrvalidator.domain.manager.usecases.ReadAppEntry
import com.google.exhibitioqrvalidator.domain.manager.usecases.ReadToken
import com.google.exhibitioqrvalidator.domain.manager.usecases.SaveAppEntry
import com.google.exhibitioqrvalidator.domain.manager.usecases.SaveUserLogin
import com.google.exhibitioqrvalidator.domain.manager.usecases.apiUseCases.loginUserasCase
import com.google.exhibitioqrvalidator.domain.manager.usecases.readUserLogin
import com.google.exhibitioqrvalidator.domain.manager.usecases.saveToken
import com.google.exhibitioqrvalidator.domain.repo.authRepository
import com.google.exhibitioqrvalidator.utils.Constants.MY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun ProvideAuthApi(): exhibitionAPi {
        return Retrofit.Builder()
            .baseUrl(MY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(exhibitionAPi::class.java)
    }
    @Provides
    @Singleton
    fun provideAPIRepository(exhibitionAPi: exhibitionAPi): authRepository{
        return authRepository(exhibitionAPi)
    }
    @Provides
    @Singleton
    fun providesApiUseCases(authRepository: authRepository): LoginUseCases {
        return LoginUseCases(
            loginUsers = loginUserasCase(authRepository)
        )
    }





    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager=
        LocalUserManagerImplementation(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        ReadAppEntry = ReadAppEntry(localUserManager),
        SaveAppEntry = SaveAppEntry(localUserManager),
        saveToken = saveToken(localUserManager),
        ClearToken = ClearToken(localUserManager),
        SaveUserLogin= SaveUserLogin(localUserManager),
        ReadToken = ReadToken(localUserManager),
        readUserLogin = readUserLogin(localUserManager)
    )
}