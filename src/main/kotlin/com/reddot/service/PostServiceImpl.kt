package com.reddot.service

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.reddot.data.entity.Post
import com.reddot.data.model.PostRequest
import com.reddot.data.model.PostResponse
import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.CommentRepository
import com.reddot.repository.PostRepository
import com.reddot.repository.SubredditRepository
import com.reddot.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val subredditRepository: SubredditRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val authService: AuthService,
) : PostService {

    override fun getAllPosts(): List<PostResponse> {
        val posts = postRepository.findAll()
        if (posts.isEmpty()) {
            throw ResourceNotFoundException("posts is empty")
        }

        return posts.map { mapToPostResponse(it) }
    }

    override fun getPost(id: Long): PostResponse {
        val post = postRepository.findById(id)
        if (post.isEmpty) {
            throw ResourceNotFoundException("post not found with $id")
        }

        return mapToPostResponse(post.get())
    }

    override fun getPostsBySubreddit(subredditId: Long): List<PostResponse> {
        val subreddit = subredditRepository.findById(subredditId)
        if (subreddit.isEmpty) {
            throw ResourceNotFoundException("post not found subreddit with $subredditId")
        }
        val posts: List<Post> = postRepository.findAllBySubreddit(subreddit.get())

        return posts.map { mapToPostResponse(it) }
    }

    override fun getPostsByUsername(username: String): List<PostResponse> {
        val user = userRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("post not found user with $username")
        val posts: List<Post> = postRepository.findByUser(user)

        return posts.map { mapToPostResponse(it) }
    }

    override fun savePost(postRequest: PostRequest): PostResponse {
        val subreddit = subredditRepository.findByName(postRequest.subredditName)
        if (subreddit.isEmpty) {
            throw ResourceNotFoundException("subreddit not found")
        }

        val post = Post(
            id = null,
            postName = postRequest.postName,
            url = postRequest.url,
            description = postRequest.description,
            voteCount = 0,
            user = authService.getCurrentUser(),
            subreddit = subreddit.get(),
            createdAt = Date(),
            updatedAt = Date()
        )
        val result: Post = postRepository.save(post)

        return mapToPostResponse(result)
    }

    private fun mapToPostResponse(post: Post): PostResponse {
        return PostResponse(
            id = post.id!!,
            postName = post.postName,
            url = post.url,
            description = post.description,
            username = authService.getCurrentUser().username,
            subredditName = post.subreddit.name,
            voteCount = post.voteCount,
            commentCount = commentCount(post),
            duration = getDuration(post)
        )
    }

    private fun commentCount(post: Post): Int {
        return commentRepository.findByPost(post).size
    }

    private fun getDuration(post: Post): String {
        return TimeAgo.using(post.createdAt.time)
    }
}