package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.LoginRequest
import com.reddot.data.model.RegisterRequest
import com.reddot.service.AuthService
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        try {
            val result = authService.register(registerRequest);
            return RestResult.build("account registration success", HttpStatus.OK, result)
        }catch(validator: ConstraintViolationException){
            return RestResult.build("account registration success", HttpStatus.BAD_REQUEST, validator)
        }catch(ex: Exception) {
            return RestResult.build("account failed to create", HttpStatus.INTERNAL_SERVER_ERROR, "registration account failed")
        }
    }

    @PostMapping("/accountVerification/{token}")
    fun verifyAccount(@PathVariable token: String): ResponseEntity<Any> {
        try {
            val result = authService.verifyAccount(token)
            return RestResult.build("account registration validate", HttpStatus.OK, result)
        } catch (ex: Exception) {
            return RestResult.build("account failed to create", HttpStatus.BAD_REQUEST, "verification account failes")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        try {
            val result = authService.login(loginRequest)
            return RestResult.build("authentication user successfully", HttpStatus.OK, result)
        } catch (ex: Exception) {
            return RestResult.build("username or password is wrong", HttpStatus.BAD_REQUEST, ex)
        }
    }
}