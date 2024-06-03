package com.edu.pucpr.servicebarber.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdatePasswordDTO(

    @field:NotBlank(message = "The newPassword cannot be blank")
    val newPassword: String,

    @field:NotNull(message = "The newPassword cannot be empty")
    val id: Long
)