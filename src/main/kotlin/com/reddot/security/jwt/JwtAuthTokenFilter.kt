package com.reddot.security.jwt

import com.reddot.security.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthTokenFilter(
    private val tokenProvider: TokenProvider,
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
           if (jwt != "" && tokenProvider.validateToken(jwt)) {
               val username: String = tokenProvider.getUserNameFromJwtToken(jwt)
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


    fun parseJwt(request: HttpServletRequest): String {
        val headerAuth: String = request.getHeader("Authorization")
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length)
        }
        return ""
    }
}