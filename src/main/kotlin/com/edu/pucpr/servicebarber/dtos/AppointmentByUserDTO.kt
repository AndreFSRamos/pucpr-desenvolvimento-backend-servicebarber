package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class AppointmentByUserDTO(
    val appointmentTime: LocalDateTime,
    val nameEnterprise: String
)
