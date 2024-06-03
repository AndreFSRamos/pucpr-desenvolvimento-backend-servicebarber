package com.edu.pucpr.servicebarber.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.Serializable

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidJwtAuthenticationException(ex: String) : AuthenticationException(ex), Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
