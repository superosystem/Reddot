package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.LoginRequest
import com.reddot.data.model.RefreshTokenRequest
import com.reddot.data.model.RegisterRequest
import com.reddot.service.AuthService
import com.reddot.service.RefreshTokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService
) {

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

    @PostMapping("/refresh/token")
    fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<Any> {
        val result = authService.refreshToken(refreshTokenRequest)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<Any> {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.refreshToken)
        return RestResult.build("SUCCESS", HttpStatus.OK, "logged out account successfully")
    }

}