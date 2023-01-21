package com.reddot.data.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    @field:Email
    val email: String?,

    @field:NotBlank
    val password: String?
)
