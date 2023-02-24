package com.reddot.service

import com.reddot.model.CommentDto
import com.reddot.model.MessageResponse

interface CommentService {
    fun create(commentDto: CommentDto) : MessageResponse
    fun findByPost(postId: Long) : List<CommentDto>
    fun findByUsername(username: String) : List<CommentDto>
}