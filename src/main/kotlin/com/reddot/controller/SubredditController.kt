package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.SubredditDTO
import com.reddot.service.SubredditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/subreddit")
class SubredditController(
    private val subredditService: SubredditService
) {

    @GetMapping
    fun getAllSubbreddit(): ResponseEntity<Any> {
        val result: List<SubredditDTO> =  subredditService.getAll()
        return RestResult.build("success", HttpStatus.OK, result)
    }

    @GetMapping("/{id}")
    fun getSubreddit(@PathVariable id: Long): ResponseEntity<Any> {
        val result: SubredditDTO = subredditService.getSubreddit(id)
        return RestResult.build("success", HttpStatus.OK, result)
    }

    @PostMapping
    fun createSubreddit(@RequestBody subredditDTO: SubredditDTO): ResponseEntity<Any> {
        val result: SubredditDTO = subredditService.save(subredditDTO)
        return RestResult.build("success", HttpStatus.OK, result)
    }

}