package com.reddot.repository

import com.reddot.entity.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long> {
    fun findByName(subredditName: String): List<Subreddit>
}