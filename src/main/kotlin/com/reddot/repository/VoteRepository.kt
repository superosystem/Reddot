package com.reddot.repository

import com.reddot.data.entity.Post
import com.reddot.data.entity.User
import com.reddot.data.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface VoteRepository : JpaRepository<Vote, Long> {
    fun findTopByPostAndUserOrderByIdDesc(post: Post, currentUser: User): Optional<Vote>
}