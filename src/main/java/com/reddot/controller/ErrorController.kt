package com.reddot.controller

import com.reddot.exception.BadRequestException
import com.reddot.exception.NotFoundException
import com.reddot.exception.UnauthorizedException
import com.reddot.model.ErrorResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationHandler(constraintViolationException: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse(constraintViolationException.message!!))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [BadRequestException::class])
    fun badrequest(badRequestException: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse(badRequestException.message!!))
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(unauthorizedException: UnauthorizedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse("Please add your API-Key"))
    }
}