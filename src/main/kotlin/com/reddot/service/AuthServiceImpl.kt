package com.reddot.service

import com.reddot.data.entity.User
import com.reddot.data.entity.VerificationToken
import com.reddot.data.model.RegisterRequest
import com.reddot.data.model.RegisterResponse
import com.reddot.repository.UserRepository
import com.reddot.repository.VerificationTokenRepository
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
    val validationUtil: ValidationUtil,
    val passwordEncoder: PasswordEncoder,
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
            return RegisterResponse(generateVerificationToken(user))
        } catch (message: Exception) {
            return RegisterResponse("user failed on register process")
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