package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.LoginRequest
import com.reddot.data.model.RegisterRequest
import com.reddot.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        val result = authService.register(registerRequest)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)

    }

    @PostMapping("/accountVerification/{token}")
    fun verifyAccount(@PathVariable token: String): ResponseEntity<Any> {
        val result = authService.verifyAccount(token)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val result = authService.login(loginRequest)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

}