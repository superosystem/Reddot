package com.reddot.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"])])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var name: String,
    var username: String,
    var email: String,
    var password: String,
    var enabled: Boolean,
    val role: String,
    var createdAt: Date,
    var updatedAt: Date?
)