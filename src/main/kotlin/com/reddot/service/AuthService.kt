package com.reddot.service

import com.reddot.data.entity.User
import com.reddot.data.model.LoginRequest
import com.reddot.data.model.LoginResponse
import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse

interface AuthService {
    fun register(registerRequest: RegisterRequest): RegisterResponse
    fun verifyAccount(token: String): String
    fun login(loginRequest: LoginRequest): LoginResponse
    fun getCurrentUser(): User
}