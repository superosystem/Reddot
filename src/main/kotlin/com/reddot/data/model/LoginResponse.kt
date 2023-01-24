package com.reddot.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class LoginResponse(
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    val token: String?,

    @field:NotBlank
    @JsonProperty("expired_at")
    var expiresAt: Instant,

    @field:NotEmpty
    @JsonProperty("refresh_token")
    var refreshToken: String
)
