package com.reddot.service

import com.reddot.data.model.PostRequest
import com.reddot.data.model.PostResponse

interface PostService {
    fun getAllPosts(): List<PostResponse>
    fun getPost(id: Long): PostResponse
    fun getPostsBySubreddit(subredditId: Long): List<PostResponse>
    fun getPostsByUsername(username: String): List<PostResponse>
    fun savePost(postRequest: PostRequest): PostResponse
}