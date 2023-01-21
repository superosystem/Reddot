package com.reddot.repository

import com.reddot.data.entity.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}