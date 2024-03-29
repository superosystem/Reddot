package com.reddot.security.jwt

import com.reddot.security.UserDetailsServiceImpl
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthTokenFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // parsing and validate jwt
            val jwt: String = parseJwt(request)
            if (jwt != "" && jwtProvider.validateToken(jwt)) {
                val username: String = jwtProvider.getUserNameFromJwtToken(jwt)
                val userDetails: UserDetails = userDetailsServiceImpl.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = authentication
            }
        }catch(ex: Exception) {
            logger.error("cannot set user authentication: $ex.message")
        }
        filterChain.doFilter(request, response)
    }


    private fun parseJwt(request: HttpServletRequest): String {
        val headerAuth: String = request.getHeader("Authorization")
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length)
        }
        return ""
    }
}