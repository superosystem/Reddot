package com.reddot.service

import com.reddot.model.SubredditDto

interface SubredditService {
    fun findAll(): List<SubredditDto>
    fun findById(id: Long): SubredditDto
    fun create(subredditDTO: SubredditDto): SubredditDto
}