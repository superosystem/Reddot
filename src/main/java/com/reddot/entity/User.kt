package com.reddot.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"])])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(name = "name")
    var name: String,
    @Column(name = "username")
    var username: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "enabled")
    var enabled: Boolean,
    @Column(name = "role")
    val role: String,
    @Column(name = "created_at")
    var createdAt: Date,
    @Column(name = "updated_at")
    var updatedAt: Date?
)