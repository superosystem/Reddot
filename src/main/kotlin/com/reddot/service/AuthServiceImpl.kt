package com.reddot.service

import com.reddot.common.Constants
import com.reddot.data.entity.User
import com.reddot.data.entity.VerificationToken
import com.reddot.data.model.LoginRequest
import com.reddot.data.model.LoginResponse
import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse
import com.reddot.data.vo.NotificationEmail
import com.reddot.exception.BadRequestException
import com.reddot.repository.UserRepository
import com.reddot.repository.VerificationTokenRepository
import com.reddot.service.mailer.MailContentBuilder
import com.reddot.service.mailer.MailServiceImpl
import com.reddot.validation.ValidationUtil
import jakarta.validation.ConstraintViolationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val tokenRepository: VerificationTokenRepository,
    val mailServiceImpl: MailServiceImpl,
    val validationUtil: ValidationUtil,
    val passwordEncoder: BCryptPasswordEncoder,
    val mailContentBuilder: MailContentBuilder,
) : AuthService {

    @Transactional
    override fun register(registerRequest: RegisterRequest): RegisterResponse {
        try {
            validationUtil.validate(registerRequest)
            val user = User(
                id = null,
                name = registerRequest.name!!,
                username = registerRequest.username!!,
                email = registerRequest.email!!,
                password = encodePassword(registerRequest.password!!),
                enabled = false,
                role = "USER",
                createdAt = Date(),
                updatedAt = null
            )
            userRepository.save(user)

            val token = generateVerificationToken(user)
            val message = mailContentBuilder
                .build("Thank you for registration to Reddot, " +
                        "please click on the below url to activate your account : " +
                        Constants.ACTIVATION_EMAIL + "/" + token)
            mailServiceImpl.sendMail(NotificationEmail("Please Active Your Account", registerRequest.email, message))

            return RegisterResponse("account registration successfully")
        } catch (validator: ConstraintViolationException) {
            return RegisterResponse(validator.message)
        } catch (exception: Exception) {
            return RegisterResponse("registration account failed")
        }
    }

    @Transactional
    override fun verifyAccount(token: String): String {
        val getTokenOptional: Optional<VerificationToken> = tokenRepository.findByToken(token)
        if (getTokenOptional.isEmpty) {
            throw BadRequestException("invalid token")
        }
        val username = getTokenOptional.get().user.username
        val user: Optional<User> = userRepository.findByUsername(username)
        if (user.isEmpty) {
            throw BadRequestException("username is not found")
        }
        try {
            user.get().enabled = true
            userRepository.save(user.get())
            return "account has been verification"
        }catch (ex: Exception) {
            throw BadRequestException("failed to verify")
        }
    }

    override fun login(loginReguest: LoginRequest): LoginResponse {

    return LoginResponse(null, null)
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