package com.edu.pucpr.servicebarber.security

import com.edu.pucpr.servicebarber.enums.UserRoleEnum
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val securityFilter: SecurityFilter,
) {
    companion object {
        private val COMMON = UserRoleEnum.COMMON.toString()
        private val MANAGER = UserRoleEnum.MANAGER.toString()
        private val ADMIN = UserRoleEnum.ADMIN.toString()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/v1/sign-in", "/api/auth/v1/sign-up-with-refresh-token").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/user/v1/sign-up").hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.GET, "/api/user/v1/find-all", "/api/user/v1/find-by-id/{id}").hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.PUT, "/api/user/v1/update-password").hasAnyRole(ADMIN)
                    .requestMatchers(HttpMethod.PATCH, "/api/user/v1/update-status/{id}").hasAnyRole(ADMIN)
                    .requestMatchers(HttpMethod.DELETE, "/api/user/v1/delete/{id}").hasAnyRole(ADMIN)
                    .requestMatchers("/users").denyAll()
                    .anyRequest().authenticated()
            }
            .cors {}
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

