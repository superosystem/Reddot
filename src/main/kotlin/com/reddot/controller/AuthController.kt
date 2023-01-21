package com.reddot.controller

import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse
import com.reddot.common.RestResult
import com.reddot.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun signup(@RequestBody registerRequest: RegisterRequest): RestResult<RegisterResponse> {
        try {
            val result = authService.signup(registerRequest);

            return RestResult(
                code = 200,
                status = "OK",
                data = result
            )
        }catch(exception: Exception) {
            return RestResult(
                code = 400,
                status = "BAD REQUEST",
                data = RegisterResponse("user failed on register account")
            )
        }


    }
}