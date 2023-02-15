package com.reddot.service

import com.reddot.model.RegisterRequest
import com.reddot.model.RegisterResponse

interface  AuthService {
    fun register(request: RegisterRequest) : RegisterResponse
}