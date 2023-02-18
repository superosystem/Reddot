package com.reddot.repository

import com.reddot.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun deleteByToken(token: String)
}