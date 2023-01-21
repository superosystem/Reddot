package com.reddot.data.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "post_name")
    private var postName: String,

    @Column(name = "url")
    private var url: String,

    @Column(name = "vote_count")
    private var voteCount: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subredditId", referencedColumnName = "id")
    private var subreddit: Subreddit,

    @Column(name = "created_at")
    private var createdAt: Date,

    @Column(name = "updated_at")
    private var updatedAt: Date?
)
