package com.reddot.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "token")
data class TokenEmail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var token: String,
    var expiryDate: Instant,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User
)