package com.edu.pucpr.servicebarber.dtos

data class EnterpriseDTO(
    val name: String,
    val address: String,
    val user: UserDTO,
    val appointments: MutableList<AppointmentByEnterpriseDTO> = mutableListOf()
) {
}