package com.reddot.repository

import com.reddot.entity.TokenEmail
import org.springframework.data.jpa.repository.JpaRepository


interface TokenEmailRepository : JpaRepository<TokenEmail, Long> {
    fun findByToken(token: String): TokenEmail?
}