package com.gusrylmubarok.reddit.backend.controller;

import com.gusrylmubarok.reddit.backend.dto.request.CommentRequest;
import com.gusrylmubarok.reddit.backend.dto.response.CommentResponse;
import com.gusrylmubarok.reddit.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.save(commentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable("id") Long id) {
        return new ResponseEntity<>(commentService.getCommentsForPost(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(commentService.getCommentsForUser(id), HttpStatus.OK);
    }

}
