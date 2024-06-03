package com.edu.pucpr.servicebarber.dtos

import jakarta.validation.constraints.NotBlank

data class SignInDTO(
    @NotBlank(message = "Please enter your username")
    val username: String,

    @NotBlank(message = "Please enter your password")
    val password: String
)
