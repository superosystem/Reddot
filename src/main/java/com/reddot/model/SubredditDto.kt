package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.reddot.entity.Subreddit
import jakarta.validation.constraints.NotBlank

data class SubredditDto(
    var id: Long?,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var description: String,

    @JsonProperty("post_count")
    var postCount: Int?
) {
    companion object {
        fun fromSubreddit(subreddit: Subreddit): SubredditDto {
            return subreddit.run {
                SubredditDto(
                    id = subreddit.id,
                    name = subreddit.name,
                    description = subreddit.description,
                    postCount = subreddit.posts?.size
                )
            }
        }
    }

}