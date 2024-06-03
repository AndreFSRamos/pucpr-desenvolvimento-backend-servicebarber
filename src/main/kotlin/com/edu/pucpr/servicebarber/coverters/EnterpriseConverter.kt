package com.edu.pucpr.servicebarber.coverters

import com.edu.pucpr.servicebarber.dtos.EnterpriseByAppointmentDTO
import com.edu.pucpr.servicebarber.dtos.EnterpriseByUserDTO
import com.edu.pucpr.servicebarber.dtos.EnterpriseDTO
import com.edu.pucpr.servicebarber.dtos.RegisterEnterpriseDTO
import com.edu.pucpr.servicebarber.entities.Enterprise
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.repositories.UserRepository
import com.edu.pucpr.servicebarber.utils.SessionUtil
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class EnterpriseConverter(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
    private val appointmentConverter: AppointmentConverter
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
            enterprise.name,
            enterprise.address,
            userConverter.userToUserDTO(enterprise.user),
            enterprise.appointments.map { appointmentConverter.appointmentToAppointmentByEnterpriseDTO(it) }.toMutableList()
        )
    }

    fun enterpriseToEnterpriseByUserDTO(enterprise: Enterprise) : EnterpriseByUserDTO {
        return EnterpriseByUserDTO(
            enterprise.name,
            enterprise.address
        )
    }

    fun enterpriseToEnterpriseByAppointmentDTO(enterprise: Enterprise) : EnterpriseByAppointmentDTO {
        return EnterpriseByAppointmentDTO(
            enterprise.name,
            enterprise.address
        )
    }
}