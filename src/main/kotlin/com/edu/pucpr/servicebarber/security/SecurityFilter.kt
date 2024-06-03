package com.edu.pucpr.servicebarber.security

import com.edu.pucpr.servicebarber.exceptions.InvalidJwtAuthenticationException
import com.edu.pucpr.servicebarber.security.jwt.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException

@Component
class SecurityFilter(private val jwtUtil: JwtUtil) : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = jwtUtil.resolveToken(request as HttpServletRequest)
        try {
            if (token != null && jwtUtil.validateToken(token)) {
                val auth = jwtUtil.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
            chain.doFilter(request, response)
        } catch (e: InvalidJwtAuthenticationException) {
            println("ERROR IN FILTER: ${e.message}")
        }
    }
}
