package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class AppointmentByEnterpriseDTO(
    val appointment : LocalDateTime,
    val nameUser: String
)
