package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.request.PostRequest;
import com.gusrylmubarok.reddit.backend.dto.response.PostResponse;
import com.gusrylmubarok.reddit.backend.exceptions.PostNotFoundException;
import com.gusrylmubarok.reddit.backend.exceptions.SubredditNotFoundException;
import com.gusrylmubarok.reddit.backend.model.*;
import com.gusrylmubarok.reddit.backend.repository.*;
import com.gusrylmubarok.reddit.backend.util.TimeAgoUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public PostResponse save(PostRequest postRequest) throws Exception {
        try{
            Post post = postRepository.save(mapToPost(postRequest));
            return mapToPostResponse(post);
        }catch(Exception e) {
            throw new Exception("Can not create post" + e);
        }
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id-" + id));
        return mapToPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToPostResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id-" + id));
        return postRepository
                .findAllBySubreddit(subreddit)
                .map(this::mapToPostResponse);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found with username-" + username));
        return postRepository
                .findByUser(user)
                .map(this::mapToPostResponse);
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getPostId())
                .postName(post.getPostTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .commentCount((int) commentRepository.findAllByPost(post).stream().count())
                .duration(TimeAgoUtils.toRelative(Date.from(post.getCreationDate()),
                        new Date(new Date().getTime() + TimeUnit.SECONDS.toMillis(1))))
                .upVote(checkVoteType(post, VoteType.UPVOTE))
                .downVote(checkVoteType(post, VoteType.DOWNVOTE))
                .build();
    }

    private Post mapToPost(PostRequest postRequest) {
        // Fine Subreddit
        Subreddit subreddit = subredditRepository.findByNameEquals(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));;
        // Make on post and add post into subreddit
        Post newPost = Post.builder()
                .postTitle(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0)
                .user(authService.getCurrentUser())
                .creationDate(Instant.now())
                .subreddit(subreddit)
                .build();
        subreddit.getPosts().add(newPost);
        return newPost;
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if(authService.isLoggedIn()) {
            Optional<Vote> voteForPostForUser = voteRepository
                    .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
            return voteForPostForUser.filter(vote ->
                    vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }
}
