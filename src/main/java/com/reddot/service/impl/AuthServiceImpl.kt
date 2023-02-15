package com.reddot.service.impl

import com.reddot.model.RegisterRequest
import com.reddot.model.RegisterResponse
import com.reddot.repository.UserRepository
import com.reddot.service.AuthService
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository
) : AuthService {
    override fun register(request: RegisterRequest): RegisterResponse {

        return RegisterResponse("Registration Success")
    }

}