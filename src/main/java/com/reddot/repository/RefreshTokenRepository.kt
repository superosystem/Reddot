package com.reddot.repository

import com.reddot.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun deleteByToken(token: String)
}