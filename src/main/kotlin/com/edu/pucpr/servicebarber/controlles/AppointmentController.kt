package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.RegisterAppointmentDTO
import com.edu.pucpr.servicebarber.services.AppointmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/appointments")
class AppointmentController @Autowired constructor(
    private val appointmentService: AppointmentService
) {

    @PostMapping
    fun save(@RequestBody dto: RegisterAppointmentDTO): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(dto))
    }

    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(appointmentService.findAll())
    }

    @DeleteMapping("/find-by-id/{id}")
    fun findBYId(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body( appointmentService.findById(id))
    }

    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body( appointmentService.deleteById(id))
    }
}
