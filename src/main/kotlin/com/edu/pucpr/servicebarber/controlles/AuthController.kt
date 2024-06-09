package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.SignInDTO
import com.edu.pucpr.servicebarber.services.AuthService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth/v1")
class AuthController @Autowired constructor(
    private val authService: AuthService
) {
    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody dto: SignInDTO): ResponseEntity<Any> {
        return ResponseEntity.ok(authService.signIn(dto))
    }

    @PostMapping("/sign-up-with-refresh-token")
    fun signUpWithRefreshToken(@RequestParam(value = "refreshToken") @NotBlank(message = "The refreshToken field cannot be empty") refreshToken: String ): ResponseEntity<Any> {
        println(refreshToken)
        return ResponseEntity.ok().body(authService.signUpWithRefreshToken(refreshToken))
    }
}
