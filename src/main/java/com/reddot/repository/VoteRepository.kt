package com.reddot.repository

import com.reddot.entity.Post
import com.reddot.entity.User
import com.reddot.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VoteRepository : JpaRepository<Vote, UUID> {
    fun findTopByPostAndUserOrderByIdDesc(post: Post, currentUser: User): Optional<Vote>
}