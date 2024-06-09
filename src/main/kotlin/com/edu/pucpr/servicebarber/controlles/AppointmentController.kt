package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.AppointmentDTO
import com.edu.pucpr.servicebarber.dtos.RegisterAppointmentDTO
import com.edu.pucpr.servicebarber.services.AppointmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/appointment/v1")
class AppointmentController @Autowired constructor(
    private val appointmentService: AppointmentService
) {

    @PostMapping
    fun save(@RequestBody dto: RegisterAppointmentDTO): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(dto))
    }

    @GetMapping
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "id,asc") sort: String
    ): ResponseEntity<Page<AppointmentDTO>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].equals("desc", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        return ResponseEntity.ok().body(appointmentService.findAll(PageRequest.of(page, size, Sort.by(sortDirection, sortParams[0]))))
    }

    @GetMapping("/find-by-id/{id}")
    fun findBYId(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body( appointmentService.findById(id))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body( appointmentService.deleteById(id))
    }
}
