package com.reddot.service

import com.reddot.data.entity.Comment
import com.reddot.data.entity.User
import com.reddot.data.model.CommentDTO
import com.reddot.data.vo.NotificationEmail
import com.reddot.exception.BadRequestException
import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.CommentRepository
import com.reddot.repository.PostRepository
import com.reddot.repository.UserRepository
import com.reddot.service.mailer.MailContentBuilder
import com.reddot.service.mailer.MailServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val mailServiceImpl: MailServiceImpl,
    private val mailContentBuilder: MailContentBuilder
) : CommentService {

    companion object {
        const val POST_URL = ""
    }

    @Transactional(readOnly = true)
    override fun getAllCommentsForPost(postId: Long): List<CommentDTO> {
        val post = postRepository.findById(postId)
        if (post.isEmpty) {
            throw ResourceNotFoundException("comment not found for post $postId")
        }
        val comments: List<Comment> = commentRepository.findByPost(post.get())

        return comments.map { mapToDTO(it) }
    }

    @Transactional(readOnly = true)
    override fun getAllCommentsForUser(username: String): List<CommentDTO> {
        val user = userRepository.findByUsername(username)
        val comments: List<Comment> = commentRepository.findAllByUser(user)

        return comments.map { mapToDTO(it) }
    }

    @Transactional
    override fun saveComment(commentDTO: CommentDTO): String {
        val post = postRepository.findById(commentDTO.postId)
        if (post.isEmpty) {
            throw ResourceNotFoundException("comment not found for post ${commentDTO.postId}")
        }
        val user = userRepository.findByUsername(authService.getCurrentUser().username)
        try {
            val comment = Comment(
                id = null,
                text = commentDTO.text,
                createdDate = Instant.now(),
                post = post.get(),
                user = user,
                createdAt = Date(),
                updatedAt = null
            )
            commentRepository.save(comment)
            val message = mailContentBuilder.build(
                message = "${post.get().user.username} posted a comment on your post $POST_URL"
            )
            sendCommentNotification(message, user)

            return "comment added"
        }catch(ex: Exception) {
            throw BadRequestException("failed to add comment")
        }
    }

    private fun mapToDTO(comment: Comment): CommentDTO {
        return CommentDTO(
            id = comment.id,
            postId = comment.post.id!!,
            createdDate = comment.createdDate,
            text = comment.text,
            username = comment.user.username
        )
    }

    private fun sendCommentNotification(message: String, user: User) {
        mailServiceImpl.sendMail(NotificationEmail(
            subject = "${user.username} comment on your post",
            recipient = user.email,
            message
        ))
    }

    private fun containsSwearWord(comment: String): Boolean {
        if (comment.contains("shit")) {
            throw BadRequestException("comments contains unacceptable language")
        }
        return false
    }

}