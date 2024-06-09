package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class AppointmentByUserDTO(
    val id: Long,
    val appointmentTime: LocalDateTime,
    val nameEnterprise: String
)
