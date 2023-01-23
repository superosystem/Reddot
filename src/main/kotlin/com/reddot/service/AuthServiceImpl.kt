package com.reddot.service

import com.reddot.common.Constants
import com.reddot.common.ValidationUtil
import com.reddot.data.entity.User
import com.reddot.data.entity.VerificationToken
import com.reddot.data.model.*
import com.reddot.data.vo.NotificationEmail
import com.reddot.exception.BadRequestException
import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.UserRepository
import com.reddot.repository.VerificationTokenRepository
import com.reddot.security.UserDetailsImpl
import com.reddot.security.jwt.TokenProvider
import com.reddot.service.mailer.MailContentBuilder
import com.reddot.service.mailer.MailServiceImpl
import jakarta.validation.ConstraintViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenRepository: VerificationTokenRepository,
    private val mailServiceImpl: MailServiceImpl,
    private val validationUtil: ValidationUtil,
    private val passwordEncoder: PasswordEncoder,
    private val mailContentBuilder: MailContentBuilder,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val refreshTokenService: RefreshTokenService
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
                role = "ROLE_USER",
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

            return RegisterResponse("account registration is successful, please open the message on the email to verify the account")
        } catch (validator: ConstraintViolationException) {
            throw BadRequestException(validator.message)
        }
    }

    @Transactional
    override fun verifyAccount(token: String): String {
        val getTokenOptional: Optional<VerificationToken> = tokenRepository.findByToken(token)
        if (getTokenOptional.isEmpty) {
            throw ResourceNotFoundException("token verification is not valid")
        }
        val username = getTokenOptional.get().user.username
        val user: User = userRepository.findByUsername(username)
        try {
            user.enabled = true
            userRepository.save(user)

            return "account verified successfully"
        }catch (ex: Exception) {
            throw BadRequestException("account failed to verify")
        }
    }

    override fun login(loginRequest: LoginRequest): LoginResponse {
        val auth: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        if (!auth.isAuthenticated) {
            throw BadRequestException("wrong username or password")
        }
        SecurityContextHolder.getContext().authentication = auth
        val  principal: UserDetailsImpl = auth.principal as UserDetailsImpl
        val token: String = tokenProvider.generateToken(auth)
        val tokenExpired = tokenProvider.getTokenExpirationInMillis()
        val refreshToken = refreshTokenService.generateRefreshToken()

        return LoginResponse(
            username = principal.username,
            token = token,
            expiresAt = Instant.now().plusMillis(tokenExpired),
            refreshToken = refreshToken.token
        )
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse {
        try {
            refreshTokenService.validateRefreshToken(refreshTokenRequest.refreshToken)
            val newToken: String = tokenProvider.generateRefreshToken(refreshTokenRequest.username)
            val tokenExpired = tokenProvider.getTokenExpirationInMillis()

            return LoginResponse(
                username = refreshTokenRequest.username,
                token = newToken,
                expiresAt = Instant.now().plusMillis(tokenExpired),
                refreshToken = refreshTokenRequest.refreshToken
            )
        }catch(ex: Exception) {
            throw BadRequestException("cannot get new token")
        }
    }

    @Transactional(readOnly = true)
    override fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        return userRepository.findByUsername(authentication.name)
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