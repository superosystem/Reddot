package com.reddot.security

import com.reddot.data.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl() : UserDetails {
    private var username: String = ""
    private var password: String = ""
    private var role: String = ""
    private var isEnable: Boolean = false

    constructor(username: String, password: String, role: String, isEnable: Boolean) : this() {
        this.username = username
        this.password = password
        this.role = role
        this.isEnable = isEnable
    }

    companion object {
        fun build(user: User): UserDetailsImpl {
            return UserDetailsImpl(
                user.username, user.password, user.role, user.enabled
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
       val authorities = ArrayList<SimpleGrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(this.role))

        return authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.isEnable
    }

}