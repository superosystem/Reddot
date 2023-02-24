package com.reddot.controller

import com.reddot.exception.BadRequestException
import com.reddot.model.*
import com.reddot.service.AuthService
import com.reddot.service.RefreshTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService
) {
    @PostMapping("/signup")
    @Throws(BadRequestException::class)
    fun signup(@RequestBody request: SignupRequest) : ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(authService.signup(request))
    }

    @PostMapping("/accountVerification/{token}")
    @Throws(BadRequestException::class)
    fun verifyEmail(@PathVariable token: String) : ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(authService.activeAccount(token))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody request: SigninRequest) : ResponseEntity<SigninResponse> {
        return ResponseEntity.ok(authService.signin(request))
    }

    @PostMapping("/refresh/token")
    fun refreshToken(@RequestBody param: RefreshTokenRequest) : ResponseEntity<SigninResponse> {
        return ResponseEntity.ok(authService.refreshToken(param))
    }

    @PostMapping("/signout")
    fun signout(@RequestBody param: RefreshTokenRequest): ResponseEntity<MessageResponse> {
        refreshTokenService.deleteRefreshToken(param.refreshToken)
        return ResponseEntity.ok(MessageResponse("signout account successfully"))
    }
}