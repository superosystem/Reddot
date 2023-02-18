package com.reddot.controller

import com.reddot.model.MessageResponse
import com.reddot.model.VoteDto
import com.reddot.service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteService: VoteService
) {
    @PostMapping
    fun vote(@RequestBody voteDto: VoteDto): ResponseEntity<MessageResponse> {
       return ResponseEntity.ok(voteService.vote(voteDto))
    }
}