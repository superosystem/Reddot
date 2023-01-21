package com.reddot.service

import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse

interface AuthService {
    fun signup(registerRequest: RegisterRequest): RegisterResponse
}