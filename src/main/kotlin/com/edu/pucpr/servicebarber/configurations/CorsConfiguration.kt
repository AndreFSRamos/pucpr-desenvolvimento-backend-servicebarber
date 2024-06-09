package com.edu.pucpr.servicebarber.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {

        registry.addMapping("/api/auth/v1/**")
            .allowedMethods("POST")
            .allowedOrigins("*")

        registry.addMapping("/api/user/v1/**")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
            .allowedOrigins("*")

        registry.addMapping("/api/enterprise/v1/**")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
            .allowedOrigins("*")

        registry.addMapping("/api/appointment/v1/**")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
            .allowedOrigins("*")
    }
}
