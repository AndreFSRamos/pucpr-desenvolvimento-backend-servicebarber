package com.edu.pucpr.servicebarber.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "enterprises")
data class Enterprise(
    val name: String,

    val address: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(mappedBy = "enterprise", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val appointments: List<Appointment> = mutableListOf()

) : BaseEntity()
