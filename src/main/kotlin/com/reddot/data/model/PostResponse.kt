package com.reddot.data.model

data class PostResponse (
    val id: Long,
    var postName: String,
    var url: String,
    var description: String,
    var username: String,
    var subredditName: String,
    var voteCount: Int,
    var commentCount: Int,
    var duration: String,

)