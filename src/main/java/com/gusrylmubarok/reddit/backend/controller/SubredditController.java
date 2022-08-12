package com.gusrylmubarok.reddit.backend.controller;

import com.gusrylmubarok.reddit.backend.dto.SubredditDto;
import com.gusrylmubarok.reddit.backend.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody @Valid SubredditDto subredditDto)
            throws Exception {
        try {
            return new ResponseEntity<>(subredditService.save(subredditDto), HttpStatus.OK);
        }catch (Exception e) {
            throw new Exception("Error creating subreddit");
        }
    }

    @GetMapping()
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return new ResponseEntity<>(subredditService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return new ResponseEntity<>(subredditService.getSubreddit(id), HttpStatus.OK);
    }
}
