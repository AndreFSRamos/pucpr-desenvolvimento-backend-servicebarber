package com.edu.pucpr.servicebarber.repositories

import com.edu.pucpr.servicebarber.entities.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Long>