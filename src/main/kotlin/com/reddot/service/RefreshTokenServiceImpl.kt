package com.reddot.service

import com.reddot.data.entity.RefreshToken
import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
@Transactional
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository
): RefreshTokenService {

    override fun generateRefreshToken(): RefreshToken {
        val refreshToken = RefreshToken(
            id = null,
            token = UUID.randomUUID().toString(),
            createDate = Instant.now(),
            createdAt = Date(),
            updatedAt = null
        )

        return refreshTokenRepository.save(refreshToken)
    }

    override fun validateRefreshToken(token: String) {
        val refreshToken = refreshTokenRepository.findByToken(token)
        if (refreshToken.isEmpty) {
            throw ResourceNotFoundException("invalid refresh token")
        }
    }

    override fun deleteRefreshToken(token: String) {
        refreshTokenRepository.deleteByToken(token)
    }


}