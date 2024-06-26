package com.edu.pucpr.servicebarber.coverters

import com.edu.pucpr.servicebarber.dtos.RegisterUserDTO
import com.edu.pucpr.servicebarber.dtos.UserByAppointmentDTO
import com.edu.pucpr.servicebarber.dtos.UserDTO
import com.edu.pucpr.servicebarber.entities.User
import com.edu.pucpr.servicebarber.enums.UserRoleEnum
import com.edu.pucpr.servicebarber.utils.SessionUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserConverter {
    fun registerUserDTOToUser(dto: RegisterUserDTO): User {
        val encoder = BCryptPasswordEncoder()

        val user = User(
            dto.username,
            encoder.encode(dto.password),
            dto.fullName,
            dto.email,
            UserRoleEnum.COMMON,
            true,
            accountNonLocked = true,
            credentialsNonExpired = true,
        )
        user.sysUserInsert = SessionUtil.getIdSessionUser()
        user.sysDateInsert = LocalDateTime.now()
        user.sysActive = true

        return user
    }

    fun userToUserDTO(user: User): UserDTO {
        return UserDTO(
            user.id!!,
            user.usernameProperty,
            user.fullName,
            user.email,
            user.sysActive,
            user.authorities.map { it.authority },
            mutableListOf(),
            mutableListOf()
        )
    }

    fun userToUserByAppointmentDTO(user: User) : UserByAppointmentDTO {
        return UserByAppointmentDTO(
            user.fullName
        )
    }
}