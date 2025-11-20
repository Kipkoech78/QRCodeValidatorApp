package com.google.exhibitioqrvalidator.domain.repo

import com.google.exhibitioqrvalidator.data.remote.exhibitionAPi
import com.google.exhibitioqrvalidator.domain.models.AttendanceResponse

class APiRepository(private val exhibitionAPi: exhibitionAPi) {
    suspend fun markAttendance(QrCoeId: String): AttendanceResponse {
        return  exhibitionAPi.MarkAttendance(QrCoeId)
    }
}