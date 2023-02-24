package com.reddot.service.impl

import com.reddot.entity.RefreshToken
import com.reddot.exception.BadRequestException
import com.reddot.repository.RefreshTokenRepository
import com.reddot.service.RefreshTokenService
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenService {
    override fun generateRefreshToken(): RefreshToken {
        val token = RefreshToken(
            id = null,
            token = UUID.randomUUID().toString(),
            createDate = Instant.now()
        )
        return refreshTokenRepository.save(token)
    }

    override fun validateRefreshToken(token: String) {
        val token = refreshTokenRepository.findByToken(token)
        if (token.isEmpty) {
            throw BadRequestException("Invalid token")
        }
    }

    override fun deleteRefreshToken(token: String) {
        refreshTokenRepository.deleteByToken(token)
    }
}