package com.reddot.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest (
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val username: String,

    @field:NotBlank  @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val confirmPassword: String?
)