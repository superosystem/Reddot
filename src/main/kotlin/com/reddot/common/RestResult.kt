package com.reddot.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RestResult {
    companion object {
        fun build(status: String, code: HttpStatus, data: Any): ResponseEntity<Any> {
            val result: MutableMap<String, Any> =  mutableMapOf(
                "code" to code.value(),
                "status" to status,
                "data" to data
            )
            return ResponseEntity<Any>(result, code)
        }
    }
}
