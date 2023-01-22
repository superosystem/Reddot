package com.reddot.data.model

import jakarta.validation.constraints.NotBlank

data class LoginResponse(
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    val token: String?
)