package com.reddot.controller

import com.reddot.common.ErrorMessage
import com.reddot.exception.*
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationHandler(constraintViolationException: ConstraintViolationException): ErrorMessage<String> {
        return ErrorMessage(
            code = 400,
            status = "BAD REQUEST",
            timestamp = Date(),
            data = constraintViolationException.message!!
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [BadRequestException::class])
    fun badRequestHandler(badRequestException: BadRequestException): ErrorMessage<String> {
        return ErrorMessage(
            code = 400,
            status = "BAD REQUEST",
            timestamp = Date(),
            data = badRequestException.message!!
        )
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun notFoundHandler(notFoundException: NotFoundException): ErrorMessage<String> {
        return ErrorMessage(
            code = 404,
            status = "NOT FOUND",
            timestamp = Date(),
            data = ""
        )
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun resourceNotFoundHandler(resourceNotFoundException: ResourceNotFoundException): ErrorMessage<String> {
        return ErrorMessage(
            code = 404,
            status = "NOT FOUND",
            timestamp = Date(),
            data = resourceNotFoundException.message!!
        )
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unAuthorizedHandler(unauthorizedException: UnauthorizedException): ErrorMessage<String> {
        return ErrorMessage(
            code = 401,
            status = "NOT FOUND",
            timestamp = Date(),
            data = "Please put your token"
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MailException::class])
    fun mailProcessHandler(mailException: MailException): ErrorMessage<String> {
        return ErrorMessage(
            code = 400,
            status = "BAD REQUEST",
            timestamp = Date(),
            data = "Failed to send activation account"
        )
    }

}