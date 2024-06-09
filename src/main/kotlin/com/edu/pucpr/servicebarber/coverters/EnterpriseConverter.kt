package com.edu.pucpr.servicebarber.coverters

import com.edu.pucpr.servicebarber.dtos.*
import com.edu.pucpr.servicebarber.entities.Enterprise
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.repositories.UserRepository
import com.edu.pucpr.servicebarber.utils.SessionUtil
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class EnterpriseConverter(
    private val userRepository: UserRepository
) {

    fun registerEnterpriseDTOToEnterprise(dto: RegisterEnterpriseDTO): Enterprise {
        return SessionUtil.getIdSessionUser()?.let { userId ->
            userRepository.findById(userId).map { user ->
                val enterprise = Enterprise(
                    dto.name,
                    dto.address,
                    user,
                    mutableListOf()
                )

                enterprise.sysActive = true
                enterprise.sysUserInsert = user.id
                enterprise.sysDateInsert = LocalDateTime.now()
                return@map enterprise
            }.orElseThrow { ResourceNotFoundException("User Session not found") }
        } ?: throw ResourceNotFoundException("User Session not found")
    }

    fun enterpriseToEnterpriseDTO(enterprise: Enterprise) : EnterpriseDTO {
        return EnterpriseDTO(
            enterprise.id!!,
            enterprise.name,
            enterprise.address,
            UserDTOByEnterpriseDTO(
                enterprise.user.id!!,
                enterprise.user.usernameProperty,
                enterprise.user.fullName,
                enterprise.user.email,
                enterprise.user.sysActive,
                enterprise.user.authorities.map { it.authority }
            ),
            enterprise.appointments.map { AppointmentByEnterpriseDTO(it.id!!, it.appointmentTime, it.user.fullName) }.toMutableList()
        )
    }

    fun enterpriseToEnterpriseByUserDTO(enterprise: Enterprise) : EnterpriseByUserDTO {
        return EnterpriseByUserDTO(
            enterprise.id!!,
            enterprise.name,
            enterprise.address
        )
    }
}