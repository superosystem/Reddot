package com.reddot.data.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "subbreddit")
data class Subreddit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts", referencedColumnName = "id")
    var posts: List<Post>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: User,

    @Column(name = "created_at")
    var createdAt: Date,

    @Column(name = "updated_at")
    var updatedAt: Date?
)
