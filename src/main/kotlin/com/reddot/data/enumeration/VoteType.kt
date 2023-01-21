package com.reddot.data.enumeration

enum class VoteType(val direction: Int) {
    UPVOTE(1), DOWNVOTE(-1),
    ;
}