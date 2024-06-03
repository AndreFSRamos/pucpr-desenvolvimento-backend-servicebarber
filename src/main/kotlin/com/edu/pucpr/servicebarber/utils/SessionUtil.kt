package com.edu.pucpr.servicebarber.utils

import com.edu.pucpr.servicebarber.entities.User
import com.edu.pucpr.servicebarber.exceptions.ResourceNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

object SessionUtil {
    @JvmStatic
    fun getIdSessionUser(): Long? {
        return Optional.ofNullable(SecurityContextHolder.getContext().authentication.principal)
            .filter { it is User }
            .map { (it as User).id }
            .orElseThrow { ResourceNotFoundException("Administrator user has not been localized.") }
    }
}