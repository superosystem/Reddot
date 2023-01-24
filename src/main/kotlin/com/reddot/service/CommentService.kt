package com.reddot.service

import com.reddot.data.model.CommentDTO

interface CommentService {
    fun getAllCommentsForPost(postId: Long): List<CommentDTO>
    fun getAllCommentsForUser(username: String): List<CommentDTO>
    fun saveComment(commentDTO: CommentDTO): String
}