package com.reddot.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RestResult {
    companion object {
        fun build(message: String, status: HttpStatus, data: Any): ResponseEntity<Any> {
            val result: MutableMap<String, Any> =  mutableMapOf(
                "message" to message,
                "status" to status.value(),
                "data" to data
            )
            return ResponseEntity<Any>(result, status)
        }
    }
}
