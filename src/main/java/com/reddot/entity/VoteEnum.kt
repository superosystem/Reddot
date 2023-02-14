package com.reddot.entity

enum class VoteEnum(val direction: Int) {
    UPVOTE(1), DOWNVOTE(-1),
    ;
}