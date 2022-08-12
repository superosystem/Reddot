package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommentServiceTest {
    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear Words")
    public void shouldNotContainSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null,null,null,null);
        assertFalse(commentService.containsSwearWords("This is a comment"));
    }

    @Test
    @DisplayName("Should Throw Exception when Exception Contains Swear Words")
    public void shouldFailWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null, null, null, null);
        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(BackendRedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}
