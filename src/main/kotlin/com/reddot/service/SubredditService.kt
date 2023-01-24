package com.reddot.service

import com.reddot.data.model.SubredditDTO

interface SubredditService {
    fun getAll(): List<SubredditDTO>
    fun getSubreddit(id: Long): SubredditDTO
    fun save(subredditDTO: SubredditDTO): SubredditDTO
}