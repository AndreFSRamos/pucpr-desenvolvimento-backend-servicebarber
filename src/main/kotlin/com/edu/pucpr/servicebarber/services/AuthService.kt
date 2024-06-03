package com.edu.pucpr.servicebarber.services

import com.edu.pucpr.servicebarber.dtos.SignInDTO
import com.edu.pucpr.servicebarber.dtos.SignInResponseDTO
import com.edu.pucpr.servicebarber.exceptions.InvalidJwtAuthenticationException
import com.edu.pucpr.servicebarber.security.jwt.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val jwtUtil: JwtUtil
) {

    fun signIn(dto: SignInDTO): SignInResponseDTO {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(dto.username, dto.password))
            val userDetails = this.userService.loadUserByUsername(dto.username)
            return jwtUtil.generateAccessToken(userDetails)
        }catch (error: AuthenticationException){
            throw InvalidJwtAuthenticationException("invalid username/password supplied!")
        }
    }

    fun signUpWithRefreshToken(refreshToken : String) : SignInResponseDTO{
        return jwtUtil.refreshToken(refreshToken)
    }
}