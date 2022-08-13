package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.SubredditDto;
import java.util.List;

public interface SubredditService {
    SubredditDto save(SubredditDto subredditDto) throws Exception;
    List<SubredditDto> getAll();
    SubredditDto getSubreddit(Long id);
}