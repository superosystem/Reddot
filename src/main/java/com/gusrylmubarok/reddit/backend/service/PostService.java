package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.request.PostRequest;
import com.gusrylmubarok.reddit.backend.dto.response.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponse save(PostRequest postRequest) throws Exception;
    PostResponse getPostById(Long id);
    List<PostResponse> getAllPosts();
    Optional<PostResponse> getPostsBySubreddit(Long id);
    Optional<PostResponse> getPostsByUsername(String username);
}
