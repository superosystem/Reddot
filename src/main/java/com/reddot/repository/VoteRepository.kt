package com.reddot.repository

import com.reddot.entity.Post
import com.reddot.entity.User
import com.reddot.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface VoteRepository : JpaRepository<Vote, Long> {
    fun findTopByPostAndUserOrderByIdDesc(post: Post, currentUser: User): Optional<Vote>
}