package com.reddot.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @NotBlank(message = "username is not empty")
    var username: String,

    @NotBlank(message = "email is not empty")
    var email: String,

    @NotBlank(message = "password is not empty")
    var password: String,

    @Column(name = "is_enable")
    var enabled: Boolean,

    @Column(name = "created_at")
    var createdAt: Date,

    @Column(name = "updated_at")
    var updatedAt: Date?
)
