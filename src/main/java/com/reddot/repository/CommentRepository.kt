package com.reddot.repository

import com.reddot.entity.Comment
import com.reddot.entity.Post
import com.reddot.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, UUID> {
    fun findByPost(post: Post): List<Comment>
    fun findAllByUser(user: User): List<Comment>
}