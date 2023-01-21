package com.reddot.data.entity

import com.reddot.data.enumeration.VoteType
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "vote")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private val id: Long,

    @Column(name = "vote_type")
    private var voteType: VoteType,

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
