package com.reddot.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PostResponse(
    val id: Long,

    var postName: String,

    var url: String,

    var description: String,

    var username: String,

    @JsonProperty("subreddit_name")
    var subredditName: String,

    @JsonProperty("vote_count")
    var voteCount: Int,

    @JsonProperty("comment_count")
    var commentCount: Int,

    var duration: String,
)