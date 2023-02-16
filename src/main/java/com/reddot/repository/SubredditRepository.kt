package com.reddot.repository

import com.reddot.entity.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubredditRepository : JpaRepository<Subreddit, UUID> {
    fun findByName(subredditName: String): List<Subreddit>
}