package com.reddot.service.impl

import com.reddot.common.Constants
import com.reddot.entity.TokenEmail
import com.reddot.entity.User
import com.reddot.exception.BadRequestException
import com.reddot.mail.MailContentService
import com.reddot.mail.MailServiceImpl
import com.reddot.model.EmailNotify
import com.reddot.model.SignupRequest
import com.reddot.model.SignupResponse
import com.reddot.repository.TokenEmailRepository
import com.reddot.repository.UserRepository
import com.reddot.service.AuthService
import com.reddot.validator.ValidationUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenEmailRepository: TokenEmailRepository,
    private val mailContentService: MailContentService,
    private val mailServiceImpl: MailServiceImpl,
    private val passwordEncoder: PasswordEncoder,
    private val validationUtil: ValidationUtil,
) : AuthService {

    @Transactional
    override fun signup(param: SignupRequest): SignupResponse {
        validationUtil.validate(param)

        if (!availableEmail(param.email)) {
            throw BadRequestException("email already exists")
        }

        if (!availableUsername(param.username)) {
            throw BadRequestException("username already exists")
        }

        val user = User(
            id = null,
            name = param.name,
            username = param.username,
            email = param.email,
            password = encodePassword(param.password),
            enabled = false,
            role = "ROLE_USER",
            createdAt = Date(),
            updatedAt = null
        )
        userRepository.save(user)
        // token
        val token = generateTokenEmail(user)
        val message = mailContentService
            .build(
                "Thank you for registration to Reddot, " +
                        "please click on the below url to activate your account : " +
                        Constants.ACTIVATION_EMAIL + "/" + token
            )
        mailServiceImpl.sendMail(EmailNotify("Please Active Your Account", param.email, message))

        return SignupResponse("Please Activation on ${param.email}")
    }

    @Transactional
    override fun activeAccount(request: String): SignupResponse {
        val token = tokenEmailRepository.findByToken(request) ?: throw BadRequestException("token is expired")
        val user = userRepository.findByUsername(token.user.username) ?: throw BadRequestException("username is not register")
        user.enabled = true
        user.updatedAt = Date()
        userRepository.save(user)

        return SignupResponse("Account has been active ${user.username}")
    }

    private fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    private fun generateTokenEmail(user: User): String {
        val token = UUID.randomUUID().toString()
        val tokenEmail = TokenEmail(
            id = null,
            token = token,
            user = user,
            expiryDate = Instant.now()
        )
        tokenEmailRepository.save(tokenEmail)
        return token
    }

    private fun availableEmail(email: String) : Boolean {
        userRepository.findByEmail(email) ?: return true
        return false
    }

    private fun availableUsername(username: String) : Boolean {
        userRepository.findByUsername(username) ?: return true
        return false
    }

}