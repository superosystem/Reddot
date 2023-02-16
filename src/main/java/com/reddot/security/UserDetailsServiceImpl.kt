package com.reddot.security

import com.reddot.exception.BadRequestException
import com.reddot.exception.NotFoundException
import com.reddot.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val user = userRepository.findByUsername(username) ?: throw BadRequestException("username is not registered")
            return UserDetailsImpl.build(user)
        }catch(ex: Exception) {
            throw NotFoundException()
        }
    }
}