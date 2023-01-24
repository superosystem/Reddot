package com.reddot.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.reddot.data.enumeration.VoteType
import jakarta.validation.constraints.NotEmpty

data class VoteDTO(
    @field:NotEmpty
    @JsonProperty("vote_type")
    val voteType: VoteType,

    @field:NotEmpty
    @JsonProperty("post_id")
    val postId: Long
)