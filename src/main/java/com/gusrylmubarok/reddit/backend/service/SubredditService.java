package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.SubredditDto;
import com.gusrylmubarok.reddit.backend.exceptions.SubredditNotFoundException;
import com.gusrylmubarok.reddit.backend.model.Subreddit;
import com.gusrylmubarok.reddit.backend.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) throws Exception {
        try {
            Subreddit subreddit = subredditRepository.save(mapDtoToSubreddit(subredditDto));
            subredditDto.setId(subreddit.getId());
            return subredditDto;
        }catch (Exception e) {
            throw new Exception("Can not save into database");
        }
    }

    @Transactional(readOnly = true)
    public Page<SubredditDto> getAll(Integer page) {
        return subredditRepository.findAll(PageRequest.of(page, 100))
                .map(this::mapToSubredditDto);
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id-" + id));
        return mapToSubredditDto(subreddit);
    }

    private SubredditDto mapToSubredditDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapDtoToSubreddit(SubredditDto subredditDto){
        return Subreddit.builder()
                .name("/r" + subredditDto.getName())
                .description(subredditDto.getDescription())
                .creationDate(Instant.now())
                .user(authService.getCurrentUser())
                .build();
    }

}