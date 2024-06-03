package com.edu.pucpr.servicebarber.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterUserDTO(

    @field:NotBlank(message = "The username cannot be blank")
    val username: String,

    @field:NotBlank(message = "The password cannot be blank")
    val password: String,

    @field:NotBlank(message = "The fullName cannot be blank")
    val fullName: String,

    @field:NotBlank(message = "The email cannot be blank")
    @field:Email(message = "The email cannot be blank")
    val email: String
)
