package com.edu.pucpr.servicebarber.dtos

data class UserDTO(
    val id: Long,
    val username: String,
    val fullName: String,
    val email: String,
    val enabled: Boolean,
    val roles: List<String>,
    var enterprises: MutableList<EnterpriseByUserDTO> = mutableListOf(),
    var appointments: MutableList<AppointmentByUserDTO> = mutableListOf()
)
