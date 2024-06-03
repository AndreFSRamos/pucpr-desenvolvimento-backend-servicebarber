package com.edu.pucpr.servicebarber.repositories

import com.edu.pucpr.servicebarber.entities.Enterprise
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EnterpriseRepository : JpaRepository<Enterprise, Long> {
}