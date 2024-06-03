package com.edu.pucpr.servicebarber.dtos

data class UserDTO(
    val username: String,
    val fullName: String,
    val email: String,
    val enabled: Boolean,
    val roles: List<String>,
    val enterprises: MutableList<EnterpriseByUserDTO> = mutableListOf(),
    val appointments: MutableList<AppointmentByUserDTO> = mutableListOf()
)
