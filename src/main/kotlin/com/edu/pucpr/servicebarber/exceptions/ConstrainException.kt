package com.edu.pucpr.servicebarber.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.Serializable

@ResponseStatus(HttpStatus.CONFLICT)
class ConstrainException(ex: String) : RuntimeException(ex), Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
