package com.edu.pucpr.servicebarber.services

import com.edu.pucpr.servicebarber.coverters.EnterpriseConverter
import com.edu.pucpr.servicebarber.dtos.EnterpriseDTO
import com.edu.pucpr.servicebarber.dtos.RegisterEnterpriseDTO
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import com.edu.pucpr.servicebarber.repositories.EnterpriseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EnterpriseService(
    private val enterpriseRepository: EnterpriseRepository,
    private val enterpriseConverter: EnterpriseConverter
) {
    fun save(dto : RegisterEnterpriseDTO){
        this.enterpriseRepository.save(enterpriseConverter.registerEnterpriseDTOToEnterprise(dto))
    }

    fun findAll(pageable: Pageable): Page<EnterpriseDTO> {
        val enterprisePage = this.enterpriseRepository.findAll(pageable)
        return enterprisePage.map { user -> this.enterpriseConverter.enterpriseToEnterpriseDTO(user) }
    }


    fun findById(id : Long) : EnterpriseDTO{
        return this.enterpriseRepository.findById(id).map { enterprise -> enterpriseConverter.enterpriseToEnterpriseDTO(enterprise) }
            .orElseThrow{ResourceNotFoundException("Enterprise not found by ID: $id")}
    }

    fun deleteById(id : Long) {
        this.findById(id)
        this.enterpriseRepository.deleteById(id)
    }
}