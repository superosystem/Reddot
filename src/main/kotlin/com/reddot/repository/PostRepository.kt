package com.reddot.repository

import com.reddot.data.entity.Post
import com.reddot.data.entity.Subreddit
import com.reddot.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllBySubreddit(subreddit: Subreddit): List<Post>
    fun findByUser(user: User): List<Post>
}