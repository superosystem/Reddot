package com.reddot.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"])])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID?,
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
) : Serializable