package com.edu.pucpr.servicebarber.services

import com.edu.pucpr.servicebarber.coverters.AppointmentConverter
import com.edu.pucpr.servicebarber.dtos.AppointmentDTO
import com.edu.pucpr.servicebarber.dtos.RegisterAppointmentDTO
import com.edu.pucpr.servicebarber.entities.Appointment
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.repositories.AppointmentRepository
import com.edu.pucpr.servicebarber.repositories.EnterpriseRepository
import com.edu.pucpr.servicebarber.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AppointmentService @Autowired constructor(
    private val appointmentRepository: AppointmentRepository,
    private val appointmentConverter: AppointmentConverter,
    private val userRepository: UserRepository,
    private val enterpriseRepository: EnterpriseRepository
) {

    fun findAll() : List<AppointmentDTO> {
        return this.appointmentRepository.findAll().map { appointment -> appointmentConverter.appointmentToAppointmentDTO(appointment) }
    }

    fun findById(id : Long) : AppointmentDTO {
        return this.appointmentRepository.findById(id).map { item -> appointmentConverter.appointmentToAppointmentDTO(item)}
            .orElseThrow{ ResourceNotFoundException("Appointment not found by ID: $id")}
    }

    fun save(dto: RegisterAppointmentDTO) {
        this.userRepository.findById(dto.idUser).map { user ->
            this.enterpriseRepository.findById(dto.idEnterprise).map { enterprise ->
                appointmentRepository.save(Appointment(enterprise, user, dto.time))
            }.orElseThrow{ResourceNotFoundException("Enterprise not found by ID: ${dto.idEnterprise}")}
        }.orElseThrow{ResourceNotFoundException("User not found by ID: ${dto.idUser}")}
    }

    fun deleteById(id: Long) {
        appointmentRepository.deleteById(id)
    }
}