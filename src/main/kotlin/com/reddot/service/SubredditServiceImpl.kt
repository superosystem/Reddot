package com.reddot.service

import com.reddot.common.ValidationUtil
import com.reddot.data.entity.Subreddit
import com.reddot.data.model.SubredditDTO
import com.reddot.data.model.SubredditDTO.Companion.from
import com.reddot.exception.BadRequestException
import com.reddot.exception.NotFoundException
import com.reddot.repository.SubredditRepository
import jakarta.validation.ConstraintViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubredditServiceImpl(
    private val subredditRepository: SubredditRepository,
    private val validationUtil: ValidationUtil,
    private val authService: AuthService,
) : SubredditService {

    @Transactional(readOnly = true)
    override fun getAll(): List<SubredditDTO> {
        val subreddits: List<Subreddit> = subredditRepository.findAll()
        if (subreddits.isEmpty()) {
            throw NotFoundException()
        }

        return subreddits.map { from(it) }
    }

    override fun getSubreddit(id: Long): SubredditDTO {
        val subreddit = subredditRepository.findByIdOrNull(id) ?: throw NotFoundException()

        return from(subreddit)
    }

    @Transactional
    override fun save(subredditDTO: SubredditDTO): SubredditDTO {
        try {
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
            val saved = subredditRepository.save(subreddit)

            return from(saved)
        }catch(validator: ConstraintViolationException) {
            throw BadRequestException(validator.message)
        }catch(ex: Exception) {
            throw BadRequestException(ex.message)
        }

    }

}