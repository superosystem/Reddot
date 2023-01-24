package com.reddot.config

import com.reddot.security.UserDetailsServiceImpl
import com.reddot.security.jwt.JwtAuthEntryPoint
import com.reddot.security.jwt.JwtAuthTokenFilter
import com.reddot.security.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val userDetailsServiceImpl: UserDetailsServiceImpl,
    private val jwtAuthEntryPoint: JwtAuthEntryPoint,
) {


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.cors()
            .and()
            .csrf().disable()   // Disable csrf because token is used.
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/v1/subreddit").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/v1/posts").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/v1/posts/**").permitAll()
            .requestMatchers("/docs/**").permitAll()
            .anyRequest().authenticated()
        http.authenticationProvider(authenticationProvider())
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(authentication: AuthenticationConfiguration): AuthenticationManager {
        return authentication.authenticationManager
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsServiceImpl)
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }

    @Bean
    fun authTokenFilter(): JwtAuthTokenFilter {
        return JwtAuthTokenFilter(tokenProvider,userDetailsServiceImpl)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}