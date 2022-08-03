package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.request.CommentRequest;
import com.gusrylmubarok.reddit.backend.dto.response.CommentResponse;
import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;
import com.gusrylmubarok.reddit.backend.exceptions.PostNotFoundException;
import com.gusrylmubarok.reddit.backend.exceptions.UserNotFoundException;
import com.gusrylmubarok.reddit.backend.model.Comment;
import com.gusrylmubarok.reddit.backend.model.NotificationEmail;
import com.gusrylmubarok.reddit.backend.model.Post;
import com.gusrylmubarok.reddit.backend.model.User;
import com.gusrylmubarok.reddit.backend.repository.CommentRepository;
import com.gusrylmubarok.reddit.backend.repository.PostRepository;
import com.gusrylmubarok.reddit.backend.repository.UserRepository;
import com.gusrylmubarok.reddit.backend.util.TimeAgoUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public CommentResponse save(CommentRequest commentRequest) {
        return mapToCommentResponse(commentRepository.save(mapToComment(commentRequest)));
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public Page<CommentResponse> getCommentsForPost(Long id, Integer page) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
        return commentRepository.findByPost(post, PageRequest.of(page, 100)).map(this::mapToCommentResponse);
    }

    public Page<CommentResponse> getCommentsForUser(Long id, Integer page) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return commentRepository.findAllByUser(user, PageRequest.of(page, 100)).map(this::mapToCommentResponse);
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new BackendRedditException("Comments contains unacceptable language");
        }
        return false;
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .postId(comment.getPost().getPostId())
                .creationDate(TimeAgoUtils.toRelative(Date.from(comment.getCreationDate()),
                        new Date(new Date().getTime() + TimeUnit.SECONDS.toMillis(1))))
                .userName(comment.getUser().getUsername())
                .build();
    }
    private Comment mapToComment(CommentRequest commentRequest) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post is not found with id-" + commentRequest.getPostId()));
        return Comment.builder()
                .text(commentRequest.getText())
                .post(post)
                .creationDate(Instant.now())
                .user(user)
                .build();
    }


}
