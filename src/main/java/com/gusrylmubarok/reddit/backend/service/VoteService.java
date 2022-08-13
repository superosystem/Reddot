package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.VoteDto;

public interface VoteService {
     void vote(VoteDto voteDto);
}