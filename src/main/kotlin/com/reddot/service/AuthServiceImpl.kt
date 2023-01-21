package com.reddot.service

import com.reddot.common.Constants
import com.reddot.data.entity.User
import com.reddot.data.entity.VerificationToken
import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse
import com.reddot.data.vo.NotificationEmail
import com.reddot.exception.BadRequestReddotException
import com.reddot.repository.UserRepository
import com.reddot.repository.VerificationTokenRepository
import com.reddot.service.mailer.MailContentBuilder
import com.reddot.service.mailer.MailService
import com.reddot.validation.ValidationUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val tokenRepository: VerificationTokenRepository,
    val mailService: MailService,
    val validationUtil: ValidationUtil,
    val passwordEncoder: PasswordEncoder,
    val mailContentBuilder: MailContentBuilder
) : AuthService {

    @Transactional
    override fun signup(registerRequest: RegisterRequest): RegisterResponse {
        try {
            validationUtil.validate(registerRequest)
            val user = User(
                id = null,
                username = registerRequest.username!!,
                email = registerRequest.email!!,
                password = encodePassword(registerRequest.password!!),
                enabled = false,
                createdAt = Date(),
                updatedAt = null
            )
            userRepository.save(user)

            val token = generateVerificationToken(user)
            val message = mailContentBuilder
                .build("Thank you for registration to Reddot, " +
                        "please click on the below url to activate your account : " +
                        Constants.ACTIVATION_EMAIL + "/" + token)
            mailService.sendMail(NotificationEmail("Please Active Your Account", registerRequest.email, message))

            return RegisterResponse(generateVerificationToken(user))
        } catch (message: Exception) {
            return RegisterResponse("user failed on register process")
        }
    }

    @Transactional
    override fun verifyAccount(token: String): String {
        val getTokenOptional: Optional<VerificationToken> = tokenRepository.findByToken(token)
        if (getTokenOptional.isEmpty) {
            throw BadRequestReddotException("invalid token")
        }
        val username = getTokenOptional.get().user.username
        val user: Optional<User> = userRepository.findByUsername(username)
        if (user.isEmpty) {
            throw BadRequestReddotException("username is not found")
        }
        try {
            user.get().enabled = true
            userRepository.save(user.get())
            return "account has been verification"
        }catch (ex: Exception) {
            throw BadRequestReddotException("failed to verify")
        }
    }

    private fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    private fun generateVerificationToken(user: User): String {
        val tokenUser: String = UUID.randomUUID().toString()
        val verificationToken = VerificationToken(
            id = null,
            token = tokenUser,
            user = user,
            expiryDate = Instant.now(),
            createdAt = Date(),
            updatedAt = null,
        )
        tokenRepository.save(verificationToken)

        return tokenUser
    }
}