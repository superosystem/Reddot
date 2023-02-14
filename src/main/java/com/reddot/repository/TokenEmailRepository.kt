package com.reddot.repository

import com.reddot.entity.TokenEmail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TokenEmailRepository : JpaRepository<TokenEmail, Long> {
    fun findByToken(token: String): Optional<TokenEmail>
}