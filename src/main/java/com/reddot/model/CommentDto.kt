package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class CommentDto (
    val id: Long?,

    @JsonProperty("post_id")
    var postId: Long,

    @JsonProperty("created_at")
    var createdDate: Instant?,

    @field:NotEmpty
    var text: String,

    var username: String
)