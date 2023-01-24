package com.reddot.data.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "post_name")
    var postName: String,

    @Column(name = "url")
    var url: String,

    var description: String,

    @Column(name = "vote_count")
    var voteCount: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subredditId", referencedColumnName = "id")
    var subreddit: Subreddit,

    @Column(name = "created_at")
    var createdAt: Date,

    @Column(name = "updated_at")
    var updatedAt: Date?
)
