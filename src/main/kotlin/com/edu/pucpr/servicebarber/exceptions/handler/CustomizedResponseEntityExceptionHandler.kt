package com.edu.pucpr.servicebarber.exceptions.handler

import com.edu.pucpr.servicebarber.exceptions.AccessException
import com.edu.pucpr.servicebarber.exceptions.ConstrainException
import com.edu.pucpr.servicebarber.exceptions.InvalidJwtAuthenticationException
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.exceptions.response.ExceptionResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler{

    @ExceptionHandler(Exception::class)
    fun handleValidationException(ex: Exception, request: HttpServletRequest): ResponseEntity<Any> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                500,
                ex.message ?: "Unexpected error",
                request.requestURI
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationMethodArgument(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<Any> {
        val errorMessage = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Validation error"
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                400,
                errorMessage,
                request.requestURI
            ),
            HttpStatus.BAD_REQUEST
        )
    }


    @ExceptionHandler(BadCredentialsException::class)
    fun handleInvalidJwtAuthenticationExceptions(
        ex: InvalidJwtAuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED,
                401,
                ex.message ?: "Invalid JWT authentication",
                request.requestURI
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(AccessDeniedException::class, PreAuthenticatedCredentialsNotFoundException::class)
    fun handleAccessDeniedException(
        ex: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                403,
                ex.message ?: "Access denied",
                request.requestURI
            ),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(AccessException::class)
    fun handleAccessDenied(ex: AccessException, request: HttpServletRequest): ResponseEntity<Any> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                403,
                ex.message ?: "Access error",
                request.requestURI
            ),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleValidationNotFound(ex: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<Any> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                404,
                ex.message ?: "Resource not found",
                request.requestURI
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(ConstrainException::class)
    fun handleConstrainException(
        ex: ConstrainException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
            ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT,
                409,
                ex.message ?: "Constraint violation",
                request.requestURI
            ),
            HttpStatus.CONFLICT
        )
    }
}
