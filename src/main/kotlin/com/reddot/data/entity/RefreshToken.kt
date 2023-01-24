package com.reddot.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_token")
data class RefreshToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var token: String,
    var createDate: Instant,
    var createdAt: Date,
    var updatedAt: Date?
)