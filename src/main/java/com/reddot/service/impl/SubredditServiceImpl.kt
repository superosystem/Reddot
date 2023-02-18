package com.reddot.service.impl

import com.reddot.entity.Subreddit
import com.reddot.exception.BadRequestException
import com.reddot.model.SubredditDto
import com.reddot.model.SubredditDto.Companion.fromSubreddit
import com.reddot.repository.SubredditRepository
import com.reddot.service.AuthService
import com.reddot.service.SubredditService
import com.reddot.validator.ValidationUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubredditServiceImpl(
    private val subredditRepository: SubredditRepository,
    private val authService: AuthService,
    private val validationUtil: ValidationUtil,
) : SubredditService {
    override fun findAll(): List<SubredditDto> {
        val subreddits = subredditRepository.findAll()
        if (subreddits.isEmpty()) {
            throw BadRequestException("")
        }
        return subreddits.map { fromSubreddit(it) }
    }

    override fun findById(id: Long): SubredditDto {
        val subreddit = subredditRepository.findByIdOrNull(id) ?: throw BadRequestException("")
        return fromSubreddit(subreddit)
    }

    override fun create(subredditDTO: SubredditDto): SubredditDto {
        validationUtil.validate(subredditDTO)

        val subreddit = Subreddit(
            id = null,
            name = "/r/${subredditDTO.name}",
            description = subredditDTO.description,
            posts = null,
            user = authService.getCurrentUser(),
            createdAt = Date(),
            updatedAt = null
        )
        return fromSubreddit(subredditRepository.save(subreddit))
    }
}