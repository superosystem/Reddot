package com.reddot.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "subreddit")
data class Subreddit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var description: String,
    var createdAt: Date,
    var updatedAt: Date?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts", referencedColumnName = "id")
    var posts: List<Post>?
)