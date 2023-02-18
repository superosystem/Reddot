package com.reddot.service.impl

import com.reddot.entity.Post
import com.reddot.entity.Vote
import com.reddot.entity.VoteEnum
import com.reddot.exception.BadRequestException
import com.reddot.model.MessageResponse
import com.reddot.model.VoteDto
import com.reddot.repository.PostRepository
import com.reddot.repository.VoteRepository
import com.reddot.service.AuthService
import com.reddot.service.VoteService
import org.springframework.stereotype.Service
import java.util.*

@Service
class VoteServiceImpl(
    private val voteRepository: VoteRepository,
    private val postRepository: PostRepository,
    private val authService: AuthService
) : VoteService {
    override fun vote(voteDto: VoteDto): MessageResponse {
        val post = postRepository.findById(voteDto.postId)
        if (post.isEmpty) {
            throw BadRequestException("not found with post ${voteDto.postId}")
        }

        val voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post.get(), authService.getCurrentUser())
        if (voteByPostAndUser.isPresent && voteByPostAndUser.get().voteType == voteDto.voteType) {
            throw BadRequestException("you have already ${voteDto.voteType}' for this post")
        }

        if (VoteEnum.UPVOTE == voteDto.voteType) {
            post.get().voteCount = post.get().voteCount + 1
        } else {
            post.get().voteCount = post.get().voteCount - 1
        }
        voteRepository.save(mapToVote(voteDto, post.get()))
        postRepository.save(post.get())

        return MessageResponse("You ${voteDto.voteType}")
    }

    private fun mapToVote(voteDTO: VoteDto, post: Post): Vote {
        return Vote(
            id = null,
            voteType = voteDTO.voteType,
            post = post,
            user = authService.getCurrentUser(),
            createdAt = Date(),
            updatedAt = Date()
        )
    }
}