package com.reddot.controller

import com.reddot.model.CommentDto
import com.reddot.model.MessageResponse
import com.reddot.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun create(@RequestBody request: CommentDto): ResponseEntity<MessageResponse> {
       return ResponseEntity.ok(commentService.create(request))
    }

    @GetMapping("/by-post/{postId}")
    fun getByPost(@PathVariable("postId") postId: Long): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.ok(commentService.findByPost(postId))
    }

    @GetMapping("/by-username/{username}")
    fun getByUsername(@PathVariable("username") username: String): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.ok(commentService.findByUsername(username))
    }
}
