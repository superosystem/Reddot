package com.reddot.repository

import com.reddot.data.entity.Comment
import com.reddot.data.entity.Post
import com.reddot.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByPost(post: Post): List<Comment>
    fun findAllByUser(user: User): List<Comment>
}