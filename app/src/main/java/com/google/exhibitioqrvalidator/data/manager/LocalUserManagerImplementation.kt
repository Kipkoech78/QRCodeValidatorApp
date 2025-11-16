package com.google.exhibitioqrvalidator.data.manager

import android.R
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.exhibitioqrvalidator.domain.manager.usecases.LocalUserManager
import com.google.exhibitioqrvalidator.utils.Constants
import com.google.exhibitioqrvalidator.utils.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class LocalUserManagerImplementation(private val context: Context): LocalUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
       return context.dataStore.data.map { preferences ->
           preferences[PreferenceKeys.APP_ENTRY]?:false
       }
    }

    override suspend fun saveUserLogin() {
        val token = context.dataStore.data.map { it[PreferenceKeys.TOKEN] }.firstOrNull()
        if(!token.isNullOrBlank()){
            context.dataStore.edit { settings->
                settings[PreferenceKeys.IsUserLoggedIn] = true
            }
        }
    }

    override fun readUserLogin(): Flow<Boolean> {
        return context.dataStore.data.map { preffs->
            preffs[PreferenceKeys.IsUserLoggedIn] ?: false
        }
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { settings->
            settings[PreferenceKeys.TOKEN] = token
        }
    }

    override fun readToken(): Flow<String> {
        return context.dataStore.data.map { token->
            token[PreferenceKeys.TOKEN] ?:""
        }
    }

    override suspend fun clearToken() {
        context.dataStore.edit { token->
            token.remove(PreferenceKeys.TOKEN)
            token[PreferenceKeys.IsUserLoggedIn] = false
            token[PreferenceKeys.APP_ENTRY] = false
        }
    }

}
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)
private object PreferenceKeys{
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY )
    val IsUserLoggedIn= booleanPreferencesKey(Constants.IS_USER_LOGGED_IN)
    val TOKEN = stringPreferencesKey("token")
}