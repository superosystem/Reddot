package com.reddot.security.jwt

import com.reddot.security.UserDetailsImpl
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider(
    @param:Value("\${jwt.secretKey}") private val secret: String,
    @Value("\${jwt.expirationInSc}") tokenValidityInSeconds: Long
) : InitializingBean {
    private val logger = LoggerFactory.getLogger(TokenProvider::class.java)
    private val tokenValidityInMilliseconds: Long
    private var key: Key? = null

    init {
        tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }

    override fun afterPropertiesSet() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    fun generateToken(authentication: Authentication): String {
        val userPrincipal: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val validity = Date(Date().time + tokenValidityInMilliseconds)

        return Jwts.builder()
            .setIssuer("self")
            .setIssuedAt(Date(Date().time))
            .setExpiration(validity)
            .setSubject(userPrincipal.username)
            .claim("scope","ROLE_USER")
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        val validity = Date(Date().time + tokenValidityInMilliseconds)

        return Jwts.builder()
            .setIssuer("self")
            .setIssuedAt(Date(Date().time))
            .setExpiration(validity)
            .setSubject(username)
            .claim("scope","ROLE_USER")
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info("invalid token signature")
        } catch (e: MalformedJwtException) {
            logger.info("invalid token")
        } catch (e: ExpiredJwtException) {
            logger.info("expired token")
        } catch (e: UnsupportedJwtException) {
            logger.info("unsupported token")
        } catch (e: IllegalArgumentException) {
            logger.info("jwt claims empty")
        }
        return false
    }

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun getTokenExpirationInMillis(): Long {
        return tokenValidityInMilliseconds
    }
}