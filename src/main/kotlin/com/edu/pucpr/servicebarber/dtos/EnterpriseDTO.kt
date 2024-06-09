package com.edu.pucpr.servicebarber.dtos

data class EnterpriseDTO(
    val id: Long,
    val name: String,
    val address: String,
    val user: UserDTOByEnterpriseDTO,
    val appointments: MutableList<AppointmentByEnterpriseDTO> = mutableListOf()
) {
}