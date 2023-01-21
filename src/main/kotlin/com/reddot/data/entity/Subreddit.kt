package com.reddot.data.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "subbreddit")
data class Subreddit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "name")
    private var name: String,

    @Column(name = "description")
    private var description: String,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts", referencedColumnName = "id")
    private var posts: List<Post>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private var user: User,

    @Column(name = "created_at")
    private var createdAt: Date,

    @Column(name = "updated_at")
    private var updatedAt: Date?
)
