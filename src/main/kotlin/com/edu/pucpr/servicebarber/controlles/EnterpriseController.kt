package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.RegisterEnterpriseDTO
import com.edu.pucpr.servicebarber.services.EnterpriseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("api/enterprise/v1")
class EnterpriseController(
    private val enterpriseService: EnterpriseService
) {

    @GetMapping
    fun findAll() : ResponseEntity<Any> {
        return ResponseEntity.ok().body(this.enterpriseService.findAll())
    }

    @GetMapping("find-by-id/{id}")
    fun findById(@PathVariable(value = "id") id: Long) : ResponseEntity<Any> {
        return ResponseEntity.ok().body(this.enterpriseService.findById(id))
    }

    @PostMapping
    fun save(dto : RegisterEnterpriseDTO) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enterpriseService.save(dto))
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enterpriseService.deleteById(id))
    }

}