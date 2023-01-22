package com.reddot.security.jwt

import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtDecoder: JwtEncoder,
    private val jwtEncoder: JwtEncoder
) {
}