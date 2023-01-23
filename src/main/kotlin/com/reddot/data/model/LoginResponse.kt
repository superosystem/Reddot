package com.reddot.data.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class LoginResponse(
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    val token: String?,

    @field:NotBlank
    var expiresAt: Instant,

    @field:NotEmpty
    var refreshToken: String
)