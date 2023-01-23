package com.reddot.service

import com.reddot.data.model.VoteDTO

interface VoteService {
    fun vote(voteDTO: VoteDTO): String
}