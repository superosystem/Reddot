package com.reddot.repository

import com.reddot.entity.Subreddit
import org.springframework.data.jpa.repository.JpaRepository


interface SubredditRepository : JpaRepository<Subreddit, Long> {
    fun findByName(subredditName: String): Subreddit?
}