package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class AppointmentDTO(
    val id: Long,
    val appointmentTime: LocalDateTime,
    val user: UserByAppointmentDTO,
    val enterprise: EnterpriseByAppointmentDTO
)
