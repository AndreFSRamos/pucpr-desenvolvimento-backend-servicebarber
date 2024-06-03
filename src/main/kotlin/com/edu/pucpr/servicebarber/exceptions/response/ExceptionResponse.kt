package com.edu.pucpr.servicebarber.exceptions.response

import org.springframework.http.HttpStatus
import java.io.Serializable
import java.time.LocalDateTime

data class ExceptionResponse(
    val timestamp: LocalDateTime,
    val error: HttpStatus,
    val status: Int,
    val message: String,
    val path: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
