package com.reddot.service

import com.reddot.data.entity.User
import com.reddot.data.model.*

interface AuthService {
    fun register(registerRequest: RegisterRequest): RegisterResponse
    fun verifyAccount(token: String): String
    fun login(loginRequest: LoginRequest): LoginResponse
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse
    fun getCurrentUser(): User
}