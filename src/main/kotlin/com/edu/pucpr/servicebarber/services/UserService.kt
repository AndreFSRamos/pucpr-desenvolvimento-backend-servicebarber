package com.edu.pucpr.servicebarber.services

import com.edu.pucpr.servicebarber.coverters.UserConverter
import com.edu.pucpr.servicebarber.dtos.RegisterUserDTO
import com.edu.pucpr.servicebarber.dtos.UpdatePasswordDTO
import com.edu.pucpr.servicebarber.dtos.UserDTO
import com.edu.pucpr.servicebarber.entities.User
import com.edu.pucpr.servicebarber.exceptions.ConstrainException
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): User {
        return this.userRepository.findByUsernameProperty(username) ?: throw ResourceNotFoundException("User not found")
    }

    fun findAll() : List<UserDTO>{
        return this.userRepository.findAll().map { user -> this.userConverter.userToUserDTO(user) }
    }

    fun findById(id: Long): UserDTO {
        return userRepository.findById(id)
            .map { user -> userConverter.userToUserDTO(user) }
            .orElseThrow { ResourceNotFoundException("User not found by ID: $id") }
    }

    fun signUp(dto: RegisterUserDTO) {
        userRepository.findByUsernameProperty(dto.username)?.let {
            throw ConstrainException("User informed already exists.")
        }
        userRepository.save(userConverter.registerUserDTOToUser(dto))
    }

    fun updatePassword(dto : UpdatePasswordDTO){
         this.userRepository.findById(dto.id).map { user ->
             val bCrypt = BCryptPasswordEncoder()
             user.passwordProperty = bCrypt.encode(dto.newPassword)
             this.userRepository.save(user)
        }.orElseThrow{ ResourceNotFoundException("User not found by ID: ${dto.id}")}
    }

    fun updateStatus(id : Long) {
        this.userRepository.findById(id).map { user ->
            user.sysActive = !user.sysActive
            this.userRepository.save(user)
        }.orElseThrow{ ResourceNotFoundException("User not found by ID: $id")}
    }

    fun deleteUserById(id: Long){
        this.findById(id)
        userRepository.deleteById(id)
    }
}