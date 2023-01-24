package com.reddot.controller

import com.reddot.common.RestResult
import com.reddot.data.model.VoteDTO
import com.reddot.service.VoteService
import org.springframework.http.HttpStatus
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
    fun vote(@RequestBody voteDTO: VoteDTO): ResponseEntity<Any> {
        val result = voteService.vote(voteDTO)
        return RestResult.build("VOTEED", HttpStatus.OK, result)
    }
}