package com.reddot.service

import com.reddot.data.entity.RefreshToken

interface RefreshTokenService{
    fun generateRefreshToken(): RefreshToken
    fun validateRefreshToken(token: String)
    fun deleteRefreshToken(token: String)
}