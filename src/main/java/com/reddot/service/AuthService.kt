package com.reddot.service

import com.reddot.model.SignupRequest
import com.reddot.model.SignupResponse

interface AuthService {
    fun signup(param: SignupRequest) : SignupResponse
    fun activeAccount(request: String): SignupResponse
}