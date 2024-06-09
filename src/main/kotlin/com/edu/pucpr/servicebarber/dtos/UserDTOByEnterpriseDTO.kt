package com.edu.pucpr.servicebarber.dtos

data class UserDTOByEnterpriseDTO(
    val id: Long,
    val username: String,
    val fullName: String,
    val email: String,
    val enabled: Boolean,
    val roles: List<String>
)
