package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.RegisterUserDTO
import com.edu.pucpr.servicebarber.dtos.UpdatePasswordDTO
import com.edu.pucpr.servicebarber.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@Controller
@RequestMapping("api/user/v1")
class UserController(private val userService: UserService) {

    @GetMapping("/find-all")
    fun getAllUsers() : ResponseEntity<Any> {
        return ResponseEntity.ok().body(this.userService.findAll())
    }

    @GetMapping("/find-by-id/{id}")
    fun getUserById(@PathVariable(value = "id") id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(this.userService.findById(id))
    }

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody dto: RegisterUserDTO): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.signUp(dto))
    }

    @PutMapping("/update-password")
    fun updatePasswordById(@Valid @RequestBody dto : UpdatePasswordDTO) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.updatePassword(dto))
    }

    @PatchMapping("/update-status/{id}")
    fun updateStatus(@PathVariable(value = "id") id: Long) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.updateStatus(id))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteUserById(@PathVariable(value = "id") id: Long) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.deleteUserById(id));
    }
}