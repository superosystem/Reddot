package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class SigninResponse(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val token: String,
    @field:NotBlank
    val expiresAt: Instant,
    @field:NotBlank
    @JsonProperty("refresh_token")
    val refreshToken: String
)