package com.gusrylmubarok.reddit.backend.service.impl;

import com.gusrylmubarok.reddit.backend.dto.VoteDto;
import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;
import com.gusrylmubarok.reddit.backend.exceptions.PostNotFoundException;
import com.gusrylmubarok.reddit.backend.model.Post;
import com.gusrylmubarok.reddit.backend.model.Vote;
import com.gusrylmubarok.reddit.backend.repository.PostRepository;
import com.gusrylmubarok.reddit.backend.repository.VoteRepository;
import com.gusrylmubarok.reddit.backend.service.AuthService;
import com.gusrylmubarok.reddit.backend.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gusrylmubarok.reddit.backend.model.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with Id-"
                        + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new BackendRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}