package com.reddot.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class CommentDTO(
    val id: Long?,

    @field:NotBlank
    @JsonProperty("post_id")
    var postId: Long,

    var createdDate: Instant?,

    @field:NotEmpty
    var text: String,

    var username: String
)