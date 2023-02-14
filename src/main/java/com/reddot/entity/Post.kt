package com.reddot.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var postName: String,
    var url: String,
    var description: String,
    var voteCount: Int,
    var createdAt: Date,
    var updatedAt: Date?,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    var subreddit: Subreddit
)