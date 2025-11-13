package com.google.exhibitioqrvalidator.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.exhibitioqrvalidator.domain.manager.usecases.LoginUseCases
import com.google.exhibitioqrvalidator.domain.models.LoginResponse
import com.google.exhibitioqrvalidator.domain.models.user
import com.google.exhibitioqrvalidator.domain.repo.authRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val response = loginUseCases.loginUsers(user(email, password))

                if (response.success) {
                    // ✅ only if API says success=true
                    _loginState.value = UiState.Success(response)
                } else {
                    // ❌ treat false as business error
                    _loginState.value = UiState.Error(response.message)
                }

            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.message ?: "Unknown error")
                e.printStackTrace()
            }
        }
    }
}
