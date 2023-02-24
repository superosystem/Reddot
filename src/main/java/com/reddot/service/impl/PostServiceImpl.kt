package com.reddot.service.impl

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.reddot.entity.Post
import com.reddot.exception.BadRequestException
import com.reddot.model.PostRequest
import com.reddot.model.PostResponse
import com.reddot.repository.CommentRepository
import com.reddot.repository.PostRepository
import com.reddot.repository.SubredditRepository
import com.reddot.repository.UserRepository
import com.reddot.service.AuthService
import com.reddot.service.PostService
import com.reddot.validator.ValidationUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val subredditRepository: SubredditRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val authService: AuthService,
    private val validationUtil: ValidationUtil,
) : PostService {
    override fun savePost(request: PostRequest): PostResponse {
        validationUtil.validate(request)

        val subreddit = subredditRepository.findByName(request.subredditName) ?: throw BadRequestException("subreddit not found")
        val post = Post(
            id = null,
            postName = request.postName,
            url = request.url,
            description = request.description,
            voteCount = 0,
            user = authService.getCurrentUser(),
            subreddit = subreddit,
            createdAt = Date(),
            updatedAt = null
        )
        return mapToPostResponse(postRepository.save(post))
    }

    override fun findAll(): List<PostResponse> {
        val posts = postRepository.findAll()
        if (posts.isEmpty()) {
            throw BadRequestException("")
        }

        return posts.map { mapToPostResponse(it) }
    }

    override fun findById(id: Long): PostResponse {
        val post = postRepository.findById(id)
        if (post.isEmpty) {
            throw BadRequestException("post not found with $id")
        }
        return mapToPostResponse(post.get())
    }

    override fun findBySubreddit(id: Long): List<PostResponse> {
        val subreddit = subredditRepository.findById(id)
        if (subreddit.isEmpty) {
            throw BadRequestException("post not found subreddit with $id")
        }
        val posts: List<Post> = postRepository.findAllBySubreddit(subreddit.get())
        return posts.map { mapToPostResponse(it) }
    }

    override fun findByUsername(username: String): List<PostResponse> {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("post not found user with $username")
        val posts: List<Post> = postRepository.findByUser(user)
        return posts.map { mapToPostResponse(it) }
    }

    private fun mapToPostResponse(post: Post): PostResponse {
        return PostResponse(
            id = post.id!!,
            postName = post.postName,
            url = post.url,
            description = post.description,
            username = post.user.username,
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