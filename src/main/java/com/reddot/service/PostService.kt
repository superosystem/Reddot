package com.reddot.service

import com.reddot.model.PostRequest
import com.reddot.model.PostResponse

interface PostService {
    fun savePost(request: PostRequest) : PostResponse
    fun findAll() : List<PostResponse>
    fun findById(id: Long) : PostResponse
    fun findBySubreddit(id: Long) : List<PostResponse>
    fun findByUsername(username: String) : List<PostResponse>
}