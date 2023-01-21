package com.reddot.data.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
@Table(name = "comment")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @NotNull
    private var text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private var user: User,

    @Column(name = "created_at")
    private var createdAt: Date,

    @Column(name = "updated_at")
    private var updatedAt: Date?
)