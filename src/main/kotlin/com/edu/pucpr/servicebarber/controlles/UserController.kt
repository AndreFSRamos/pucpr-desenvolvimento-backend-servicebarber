package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.RegisterUserDTO
import com.edu.pucpr.servicebarber.dtos.UpdatePasswordDTO
import com.edu.pucpr.servicebarber.dtos.UserDTO
import com.edu.pucpr.servicebarber.services.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort

@Controller
@RequestMapping("api/user/v1")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/find-all")
    fun getAllUsers(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "id,asc") sort: String
    ): ResponseEntity<Page<UserDTO>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].equals("desc", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        return ResponseEntity.ok(userService.findAll(PageRequest.of(page, size, Sort.by(sortDirection, sortParams[0]))))
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
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.deleteUserById(id))
    }
}