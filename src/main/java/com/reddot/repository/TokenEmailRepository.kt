package com.reddot.repository

import com.reddot.entity.TokenEmail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenEmailRepository : JpaRepository<TokenEmail, UUID> {
    fun findByToken(token: String): TokenEmail?
}