package com.reddot.repository

import com.reddot.data.entity.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long> {
    fun findByName(subredditName: String): Optional<Subreddit>
}