package com.reddot.service

import com.reddot.model.MessageResponse
import com.reddot.model.VoteDto

interface VoteService {
    fun vote(voteDTO: VoteDto): MessageResponse
}