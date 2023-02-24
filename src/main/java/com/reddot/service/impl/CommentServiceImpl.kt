package com.reddot.service.impl

import com.reddot.entity.Comment
import com.reddot.entity.User
import com.reddot.exception.BadRequestException
import com.reddot.mail.MailContentService
import com.reddot.mail.MailServiceImpl
import com.reddot.model.CommentDto
import com.reddot.model.EmailNotify
import com.reddot.model.MessageResponse
import com.reddot.repository.CommentRepository
import com.reddot.repository.PostRepository
import com.reddot.repository.UserRepository
import com.reddot.service.AuthService
import com.reddot.service.CommentService
import com.reddot.validator.ValidationUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val mailServiceImpl: MailServiceImpl,
    private val mailContentService: MailContentService,
    private val validationUtil: ValidationUtil,
) : CommentService {
    companion object {
        const val POST_URL = ""
    }

    @Transactional
    override fun create(commentDto: CommentDto): MessageResponse {
        validationUtil.validate(commentDto)
        val post = postRepository.findById(commentDto.postId)
        if (post.isEmpty) {
            throw BadRequestException("comment not found for post ${commentDto.postId}")
        }
        val user = userRepository.findByUsername(authService.getCurrentUser().username) ?: throw BadRequestException("username is not found")

        val comment = Comment(
            id = null,
            text = commentDto.text,
            createdAt = Date(),
            updatedAt = null,
            post = post.get(),
            user = user
        )
        commentRepository.save(comment)

        val message = mailContentService.build("${post.get().user.username} posted a comment on your post ${post.get().url}")
        sendCommentNotification(message, user)

        return MessageResponse("comment added")

    }

    override fun findByPost(postId: Long): List<CommentDto> {
        val post = postRepository.findById(postId)
        if (post.isEmpty) {
            throw BadRequestException("comment not found for post $postId")
        }

        val comments: List<Comment> = commentRepository.findByPost(post.get())
        return comments.map { mapToDTO(it) }
    }

    override fun findByUsername(username: String): List<CommentDto> {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("username not found")

        val comments: List<Comment> = commentRepository.findAllByUser(user)
        return comments.map { mapToDTO(it) }
    }

    private fun mapToDTO(comment: Comment): CommentDto {
        return CommentDto(
            id = comment.id,
            postId = comment.post.id!!,
            createdDate = comment.createdAt.toInstant(),
            text = comment.text,
            username = comment.user.username
        )
    }

    private fun sendCommentNotification(message: String, user: User) {
        mailServiceImpl.sendMail(EmailNotify(
            subject = "${user.username} comment on your post",
            recipient = user.email,
            message
        )
        )
    }

    private fun containsSwearWord(comment: String): Boolean {
        if (comment.contains("shit")) {
            throw BadRequestException("comments contains unacceptable language")
        }
        return false
    }
}