package com.reddot.data.model

import com.reddot.data.entity.Subreddit
import jakarta.validation.constraints.NotBlank

data class SubredditDTO(
    val id: Long?,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var description: String,

    var postCount: Int?
) {
    companion object {
        fun from(subreddit: Subreddit): SubredditDTO {
            return subreddit.run {
                SubredditDTO(
                    id = subreddit.id,
                    name = subreddit.name,
                    description = subreddit.description,
                    postCount = subreddit.posts?.size
                )
            }
        }
    }
}
