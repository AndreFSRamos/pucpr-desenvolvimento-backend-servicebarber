package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class AppointmentDTO(
    val appointmentTime: LocalDateTime,
    val user: UserByAppointmentDTO,
    val enterprise: EnterpriseByAppointmentDTO
)
