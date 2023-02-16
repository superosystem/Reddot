package com.reddot.repository

import com.reddot.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.*

@EnableJpaRepositories
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}