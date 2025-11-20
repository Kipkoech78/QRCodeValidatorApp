package com.google.exhibitioqrvalidator.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.exhibitioqrvalidator.domain.manager.usecases.MarkAttendanceUseCases

import com.google.exhibitioqrvalidator.domain.models.EventOrderData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val markAttendanceCase: MarkAttendanceUseCases
) : ViewModel() {

    private val _uiMessage = MutableStateFlow("Scan QR Code")
    val uiMessage = _uiMessage.asStateFlow()

    private val _ticketInfo = MutableStateFlow<EventOrderData?>(null)
    val ticketInfo = _ticketInfo.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun markAttendance(qrCodeId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _uiMessage.value = "Checking ticket..."

                val response = markAttendanceCase.markAttendance(qrCodeId)

                if (response.success == true) {   // Adjust to your API response model
                    _ticketInfo.value = response.data
                    _uiMessage.value = "Attendance Marked!"
                } else {
                    _uiMessage.value = response.message ?: "Invalid Ticket"
                }

            } catch (e: Exception) {
                _uiMessage.value = "Network error: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}
