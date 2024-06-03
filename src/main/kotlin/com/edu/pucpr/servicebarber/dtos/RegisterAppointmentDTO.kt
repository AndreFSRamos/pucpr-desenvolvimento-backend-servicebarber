package com.edu.pucpr.servicebarber.dtos

import java.time.LocalDateTime

data class RegisterAppointmentDTO(
        val time: LocalDateTime,
        val idEnterprise: Long,
        val idUser: Long
) {
}