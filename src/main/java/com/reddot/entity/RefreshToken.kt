package com.reddot.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_token")
data class RefreshToken (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID,
    var token: String,
    var createDate: Instant,
) : Serializable