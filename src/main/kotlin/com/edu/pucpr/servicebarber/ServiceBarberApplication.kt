package com.edu.pucpr.servicebarber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class ServiceBarberApplication

fun main(args: Array<String>) {
	runApplication<ServiceBarberApplication>(*args)
}
