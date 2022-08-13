package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.request.CommentRequest;
import com.gusrylmubarok.reddit.backend.dto.response.CommentResponse;
import com.gusrylmubarok.reddit.backend.model.User;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentRequest commentRequest);
    void sendCommentNotification(String message, User user);
    List<CommentResponse> getCommentsForPost(Long id);
    List<CommentResponse> getCommentsForUser(Long id);
    boolean containsSwearWords(String comment);
}
