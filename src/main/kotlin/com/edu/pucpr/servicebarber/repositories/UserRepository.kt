package com.edu.pucpr.servicebarber.repositories

import com.edu.pucpr.servicebarber.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsernameProperty(username: String): User?
}
