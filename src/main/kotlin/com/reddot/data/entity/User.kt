package com.reddot.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @NotBlank(message = "username is not empty")
    private var username: String,

    @NotBlank(message = "email is not empty")
    private var email: String,

    @NotBlank(message = "password is not empty")
    private var password: String,

    @Column(name = "is_enable")
    private var enabled: Boolean,

    @Column(name = "created_at")
    private var createdAt: Date,

    @Column(name = "updated_at")
    private var updatedAt: Date?
)
