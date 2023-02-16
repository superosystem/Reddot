package com.reddot.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.Instant
import java.util.*

@Entity
@Table(name = "token")
data class TokenEmail(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID?,
    var token: String,
    var expiryDate: Instant,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User
) : Serializable