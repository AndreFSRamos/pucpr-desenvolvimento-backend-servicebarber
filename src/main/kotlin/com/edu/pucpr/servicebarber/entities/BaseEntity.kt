package com.edu.pucpr.servicebarber.entities

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var sysUserInsert: Long? = null

    var sysDateInsert: LocalDateTime? = null

    var sysUserUpdate: Long? = null

    var sysDateUpdate: LocalDateTime? = null

    var sysActive: Boolean = true

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        sysDateInsert = now
        sysDateUpdate = now
    }

    @PreUpdate
    fun preUpdate() {
        sysDateUpdate = LocalDateTime.now()
    }
}