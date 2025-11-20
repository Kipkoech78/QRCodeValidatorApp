package com.google.exhibitioqrvalidator.domain.models

data class AttendanceResponse(
    val success: Boolean,
    val message: String,
    val data: EventOrderData?
)

data class EventOrderData(
    val _id: String,
    val userId: String,
    val eventId: String,
    val ticketCount: Int,
    val totalAmount: Double,
    val paymentMethod: String,
    val paymentStatus: String,
    val eventStatus: String,
    val qrCodeId: String,
    val qrCode: String,
    val payerId: String?,
    val paymentId: String?,
    val paymentDate: String?
)

