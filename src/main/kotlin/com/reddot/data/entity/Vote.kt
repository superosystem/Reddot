package com.reddot.data.entity

import com.reddot.data.enumeration.VoteType
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "vote")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,

    @Column(name = "vote_type")
    var voteType: VoteType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: User,

    @Column(name = "created_at")
    var createdAt: Date,

    @Column(name = "updated_at")
    var updatedAt: Date?
)
