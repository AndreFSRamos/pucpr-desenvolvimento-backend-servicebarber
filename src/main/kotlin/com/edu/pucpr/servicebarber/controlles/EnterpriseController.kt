package com.edu.pucpr.servicebarber.controlles

import com.edu.pucpr.servicebarber.dtos.RegisterEnterpriseDTO
import com.edu.pucpr.servicebarber.services.EnterpriseService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("api/enterprise/v1")
class EnterpriseController(
    private val enterpriseService: EnterpriseService
) {

    @GetMapping
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "id,asc") sort: String
    ) : ResponseEntity<Any> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].equals("desc", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        return ResponseEntity.ok().body(this.enterpriseService.findAll(PageRequest.of(page, size, Sort.by(sortDirection, sortParams[0]))))
    }

    @GetMapping("find-by-id/{id}")
    fun findById(@PathVariable(value = "id") id: Long) : ResponseEntity<Any> {
        return ResponseEntity.ok().body(this.enterpriseService.findById(id))
    }

    @PostMapping
    fun save(@RequestBody dto : RegisterEnterpriseDTO) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enterpriseService.save(dto))
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enterpriseService.deleteById(id))
    }

}