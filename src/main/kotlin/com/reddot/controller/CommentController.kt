package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.CommentDTO
import com.reddot.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(@RequestBody commentDTO: CommentDTO): ResponseEntity<Any> {
        val result = commentService.saveComment(commentDTO)
        return RestResult.build("CREATED", HttpStatus.CREATED, result)
    }

    @GetMapping("/by-post/{postId}")
    fun getAllCommentsForPost(@PathVariable("postId") postId: Long): ResponseEntity<Any> {
        val result = commentService.getAllCommentsForPost(postId)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @GetMapping("/by-user/{username}")
    fun getAllCommentsByUser(@PathVariable("username") username: String): ResponseEntity<Any> {
        val result = commentService.getAllCommentsForUser(username)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

}