package com.edu.pucpr.servicebarber.coverters

import com.edu.pucpr.servicebarber.dtos.AppointmentByEnterpriseDTO
import com.edu.pucpr.servicebarber.dtos.AppointmentByUserDTO
import com.edu.pucpr.servicebarber.dtos.AppointmentDTO
import com.edu.pucpr.servicebarber.entities.Appointment
import org.springframework.stereotype.Component

@Component
class AppointmentConverter(
    private val converterUser: UserConverter,
    private val converterEnterprise: EnterpriseConverter
) {

    fun appointmentToAppointmentByUserDTO(appointment: Appointment) : AppointmentByUserDTO {
        return AppointmentByUserDTO(
            appointment.appointmentTime,
            appointment.enterprise.name
        )
    }

    fun appointmentToAppointmentByEnterpriseDTO(appointment: Appointment) : AppointmentByEnterpriseDTO{
        return AppointmentByEnterpriseDTO(
            appointment.appointmentTime,
            appointment.user.fullName
        )
    }

    fun appointmentToAppointmentDTO(appointment: Appointment) : AppointmentDTO {
        return AppointmentDTO(
            appointment.appointmentTime,
            converterUser.userToUserByAppointmentDTO(appointment.user),
            converterEnterprise.enterpriseToEnterpriseByAppointmentDTO(appointment.enterprise)
        )
    }
}