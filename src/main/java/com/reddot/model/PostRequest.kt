package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

data class PostRequest(
    val id: Long?,

    @field:NotEmpty
    @JsonProperty("subreddit_name")
    var subredditName: String,

    @field:NotEmpty
    @JsonProperty("post_name")
    var postName: String,

    var url: String,

    @field:NotEmpty
    var description: String
)