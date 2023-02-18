package com.reddot.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_token")
data class RefreshToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var token: String,
    var createDate: Instant,
)