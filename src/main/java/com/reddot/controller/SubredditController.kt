package com.reddot.controller

import com.reddot.model.SubredditDto
import com.reddot.service.SubredditService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subreddit")
class SubredditController(
    private val subredditServ: SubredditService
) {
    @GetMapping
    fun getAllSubreddit(): ResponseEntity<List<SubredditDto>> {
        return ResponseEntity.ok(subredditServ.findAll())
    }

    @GetMapping("/{id}")
    fun getSubreddit(@PathVariable id: Long): ResponseEntity<SubredditDto> {
        return ResponseEntity.ok(subredditServ.findById(id))
    }

    @PostMapping
    fun create(@RequestBody param: SubredditDto) : ResponseEntity<SubredditDto> {
        return ResponseEntity.ok(subredditServ.create(param))
    }
}