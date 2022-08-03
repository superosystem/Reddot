package com.gusrylmubarok.reddit.backend.controller;

import com.gusrylmubarok.reddit.backend.dto.request.PostRequest;
import com.gusrylmubarok.reddit.backend.dto.response.PostResponse;
import com.gusrylmubarok.reddit.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) throws Exception {
        try{
            return new ResponseEntity<>(postService.save(postRequest), HttpStatus.CREATED);
        }catch(Exception e){
            throw new Exception("Cannot create a post " + e);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(@RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getAllPosts(page.orElse(0)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/sub/{id}")
    public ResponseEntity<Page<PostResponse>> getPostsBySubreddit(@PathVariable("id") Long id,
                                                                  @RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getPostsBySubreddit(page.orElse(0), id), HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Page<PostResponse>> getPostsByUsername(@PathVariable("name") String username,
                                                                 @RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getPostsByUsername(username, page.orElse(0)), HttpStatus.OK);
    }
}
