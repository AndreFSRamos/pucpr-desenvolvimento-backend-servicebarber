package com.edu.pucpr.servicebarber.security.jwt

import com.edu.pucpr.servicebarber.dtos.SignInResponseDTO
import com.edu.pucpr.servicebarber.entities.User
import com.edu.pucpr.servicebarber.repositories.UserRepository
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.edu.pucpr.servicebarber.exceptions.InvalidJwtAuthenticationException
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException

@Service
class JwtUtil(
    private val userDetailsService: UserDetailsService,
    private val userRepository: UserRepository
) {
    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey: String = "secret"

    @Value("\${security.jwt.token.access-token.expire-length:3600000}")
    private var accessTokenValidityInMilliseconds: Long = 3600000 // 1h

    @Value("\${security.jwt.token.refresh-token.expire-length:3600000}")
    private var refreshTokenValidityInMilliseconds: Long = 3600000 * 3 // 3h

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey)
    }

    fun generateAccessToken(user: User): SignInResponseDTO {
        val now = Date()
        val validity = Date(now.time + accessTokenValidityInMilliseconds)
        return SignInResponseDTO(
            username = user.usernameProperty,
            authenticated = true,
            created = now,
            expiration = validity,
            accessToken = getAccessToken(user, now, validity),
            refreshToken = getRefreshToken(user, now)
        )
    }

    fun refreshToken(refreshToken: String): SignInResponseDTO {
        val token = if (refreshToken.startsWith("Bearer ")) refreshToken.substring("Bearer ".length) else refreshToken

        this.validateToken(token)

        return userRepository.findByUsernameProperty(JWT.require(algorithm).build().verify(token).subject)
            ?.let { generateAccessToken(it) }
            ?: throw ResourceNotFoundException("Username not found!")
    }

    private fun getAccessToken(user: User, now: Date, validity: Date): String {
        return JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(user.usernameProperty)
            .withClaim("id", user.id)
            .withClaim("role", user.role.toString())
            .withIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString())
            .sign(algorithm)
    }

    private fun getRefreshToken(user: User, now: Date): String {
        return JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + refreshTokenValidityInMilliseconds))
            .withSubject(user.usernameProperty)
            .withClaim("id", user.id)
            .withClaim("role", user.role.toString())
            .sign(algorithm)
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedToken(token).subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun decodedToken(token: String): DecodedJWT {
        return JWT.require(algorithm).build().verify(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) bearerToken.substring("Bearer ".length) else bearerToken
    }

    fun validateToken(token: String): Boolean {
        return try {
            !decodedToken(token).expiresAt.before(Date())
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token!")
        }
    }
}


