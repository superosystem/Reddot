package com.reddot.data.model

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    val password: String?
)