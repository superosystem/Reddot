package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.PostRequest
import com.reddot.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody postRequest: PostRequest) : ResponseEntity<Any> {
        val result = postService.savePost(postRequest)
        return RestResult.build("CREATED", HttpStatus.CREATED, result)
    }

    @GetMapping
    fun getAllPosts() : ResponseEntity<Any> {
        val result = postService.getAllPosts()
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @GetMapping("/{id}")
    fun getAPost(@PathVariable("id") id: Long) : ResponseEntity<Any> {
        val result = postService.getPost(id)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @GetMapping("/by-subreddit/{id}")
    fun getPostBySubreddit(@PathVariable("id") id: Long) : ResponseEntity<Any> {
        val result = postService.getPostsBySubreddit(id)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }

    @GetMapping("/by-user/{username}")
    fun getPostByUser(@PathVariable("username") username: String) : ResponseEntity<Any> {
        val result = postService.getPostsByUsername(username)
        return RestResult.build("SUCCESS", HttpStatus.OK, result)
    }
}