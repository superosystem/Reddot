package com.reddot.security

import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails  {
        val user = userRepository.findByUsername(username)
        if (user.isEmpty) {
            throw ResourceNotFoundException("user is not found with $username")
        }

        return UserDetailsImpl.build(user.get())
    }
}