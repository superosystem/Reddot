package com.reddot.service

import com.reddot.data.entity.Post
import com.reddot.data.entity.Vote
import com.reddot.data.enumeration.VoteType
import com.reddot.data.model.VoteDTO
import com.reddot.exception.BadRequestException
import com.reddot.exception.ResourceNotFoundException
import com.reddot.repository.PostRepository
import com.reddot.repository.VoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class VoteServiceImpl(
    private val voteRepository: VoteRepository,
    private val postRepository: PostRepository,
    private val authService: AuthService
) : VoteService {

    @Transactional
    override fun vote(voteDTO: VoteDTO): String {
        try {
            val post = postRepository.findById(voteDTO.postId)
            if (post.isEmpty) {
                throw ResourceNotFoundException("not found with post ${voteDTO.postId}")
            }

            val voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByIdDesc(post.get(), authService.getCurrentUser())
            if (voteByPostAndUser.isPresent && voteByPostAndUser.get().voteType == voteDTO.voteType) {
                throw BadRequestException("you have already ${voteDTO.voteType}' for this post")
            }
            if (VoteType.UPVOTE == voteDTO.voteType) {
                post.get().voteCount = post.get().voteCount + 1
            } else {
                post.get().voteCount = post.get().voteCount - 1
            }
            voteRepository.save(mapToVote(voteDTO, post.get()))
            postRepository.save(post.get())

            return "You ${voteDTO.voteType}"
        }catch (ex: Exception) {
            throw BadRequestException("failed to vote on post")
        }
    }

    private fun mapToVote(voteDTO: VoteDTO, post: Post): Vote {
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