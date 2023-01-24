package com.reddot.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank
    @JsonProperty("refresh_token")
    val refreshToken: String,

    @field:NotBlank
    var username: String
)