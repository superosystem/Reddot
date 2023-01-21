package com.reddot.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "token")
data class VerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @Column(name = "token")
    private var token: String,

    @Column(name = "expiry_date")
    private var expiryDate: Instant,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private var user: User,

    @Column(name = "created_at")
    private var createdAt: Date,

    @Column(name = "updated_at")
    private var updatedAt: Date?
)
