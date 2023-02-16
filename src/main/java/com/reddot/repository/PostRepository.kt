package com.reddot.repository

import com.reddot.entity.Post
import com.reddot.entity.Subreddit
import com.reddot.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, UUID> {
    fun findAllBySubreddit(subreddit: Subreddit): List<Post>
    fun findByUser(user: User): List<Post>
}