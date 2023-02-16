package com.reddot.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "post")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID,
    var postName: String,
    var url: String,
    var description: String,
    var voteCount: Int,
    var createdAt: Date,
    var updatedAt: Date?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    var subreddit: Subreddit
) : Serializable