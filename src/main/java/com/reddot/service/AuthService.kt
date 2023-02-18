package com.reddot.service

import com.reddot.entity.User
import com.reddot.model.*

interface AuthService {
    fun signup(param: SignupRequest): MessageResponse
    fun activeAccount(request: String): MessageResponse
    fun signin(param: SigninRequest): SigninResponse
    fun refreshToken(param: RefreshTokenRequest) : SigninResponse
    fun getCurrentUser(): User
}