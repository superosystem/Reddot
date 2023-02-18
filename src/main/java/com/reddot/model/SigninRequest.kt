package com.reddot.model

import jakarta.validation.constraints.NotBlank

data class SigninRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)