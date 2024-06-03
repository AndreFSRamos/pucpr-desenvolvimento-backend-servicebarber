package com.edu.pucpr.servicebarber.dtos

import java.util.Date

data class SignInResponseDTO(
    val username : String,
    val authenticated: Boolean,
    val created: Date,
    val expiration: Date,
    val accessToken: String,
    val refreshToken: String
)
