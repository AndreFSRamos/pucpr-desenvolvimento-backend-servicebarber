package com.edu.pucpr.servicebarber.coverters

import com.edu.pucpr.servicebarber.dtos.AppointmentByUserDTO
import com.edu.pucpr.servicebarber.dtos.AppointmentDTO
import com.edu.pucpr.servicebarber.dtos.EnterpriseByAppointmentDTO
import com.edu.pucpr.servicebarber.entities.Appointment
import org.springframework.stereotype.Component

@Component
class AppointmentConverter(
    private val converterUser: UserConverter
) {

    fun appointmentToAppointmentByUserDTO(appointment: Appointment) : AppointmentByUserDTO {
        return AppointmentByUserDTO(
            appointment.id!!,
            appointment.appointmentTime,
            appointment.enterprise.name
        )
    }

    fun appointmentToAppointmentDTO(appointment: Appointment) : AppointmentDTO {
        return AppointmentDTO(
            appointment.id!!,
            appointment.appointmentTime,
            converterUser.userToUserByAppointmentDTO(appointment.user),
            EnterpriseByAppointmentDTO(
                appointment.enterprise.id!!,
                appointment.enterprise.name,
                appointment.enterprise.address
            )
        )
    }
}