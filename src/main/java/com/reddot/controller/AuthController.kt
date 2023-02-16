package com.reddot.controller

import com.reddot.exception.BadRequestException
import com.reddot.model.SignupRequest
import com.reddot.model.SignupResponse
import com.reddot.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.Throws

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService,
) {
    @PostMapping("/signup")
    @Throws(BadRequestException::class)
    fun signup(@RequestBody request: SignupRequest) : ResponseEntity<SignupResponse> {
        val result = authService.signup(request)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/accountVerification/{token}")
    @Throws(BadRequestException::class)
    fun verifyEmail(@PathVariable token: String) : ResponseEntity<SignupResponse> {
        val result = authService.activeAccount(token)
        return ResponseEntity.ok(result)
    }
}