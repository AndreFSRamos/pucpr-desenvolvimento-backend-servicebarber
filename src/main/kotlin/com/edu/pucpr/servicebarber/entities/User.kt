package com.edu.pucpr.servicebarber.entities

import com.edu.pucpr.servicebarber.enums.UserRoleEnum
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User (

    @Column(name = "username")
    var usernameProperty: String,

    @Column(name = "password")
    var passwordProperty: String,

    var fullName: String,

    var email: String,

    @Enumerated(EnumType.STRING)
    var role: UserRoleEnum,

    var accountNonExpired: Boolean,

    var accountNonLocked: Boolean,

    var credentialsNonExpired: Boolean,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var enterprises: List<Enterprise> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var appointments: List<Appointment> = mutableListOf()

) : BaseEntity(), UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return when (role) {
            UserRoleEnum.COMMON -> listOf(SimpleGrantedAuthority("ROLE_COMMON"))
            UserRoleEnum.MANAGER -> listOf(SimpleGrantedAuthority("ROLE_MANAGER"), SimpleGrantedAuthority("ROLE_COMMON"))
            UserRoleEnum.ADMIN -> listOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_MANAGER"), SimpleGrantedAuthority("ROLE_COMMON"))
        }
    }

    override fun getPassword(): String {
        return passwordProperty
    }

    override fun getUsername(): String {
        return usernameProperty
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return sysActive
    }
}