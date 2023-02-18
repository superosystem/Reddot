package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.reddot.entity.VoteEnum
import jakarta.validation.constraints.NotEmpty

data class VoteDto(
    @field:NotEmpty
    @JsonProperty("vote_type")
    val voteType: VoteEnum,

    @field:NotEmpty
    @JsonProperty("post_id")
    val postId: Long
)