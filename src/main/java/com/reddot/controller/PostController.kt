package com.reddot.controller

import com.reddot.model.PostRequest
import com.reddot.model.PostResponse
import com.reddot.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun create(@RequestBody request: PostRequest) : ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.savePost(request))
    }

    @GetMapping
    fun getAll() : ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(postService.findAll())
    }

    @GetMapping("/{id}")
    fun getA(@PathVariable("id") id: Long) : ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.findById(id))
    }

    @GetMapping("/by-subreddit/{id}")
    fun getBySubreddit(@PathVariable("id") id: Long) : ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(postService.findBySubreddit(id))
    }

    @GetMapping("/by-username/{username}")
    fun getByUsername(@PathVariable("username") username: String) : ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(postService.findByUsername(username))
    }
}