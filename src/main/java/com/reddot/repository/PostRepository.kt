package com.reddot.repository

import com.reddot.entity.Post
import com.reddot.entity.Subreddit
import com.reddot.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface PostRepository : JpaRepository<Post, Long> {
    fun findAllBySubreddit(subreddit: Subreddit): List<Post>
    fun findByUser(user: User): List<Post>
}