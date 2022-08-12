package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.Comment;
import com.gusrylmubarok.reddit.backend.model.Post;
import com.gusrylmubarok.reddit.backend.model.Subreddit;
import com.gusrylmubarok.reddit.backend.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CommentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Should Found Comment By Post")
    public void shouldFoundCommentByPost() {
        User user =  new User(null, "User 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, user, Instant.now(), subreddit);
        entityManager.persist(post);
        Comment comment = new Comment(null, "Comment Content", post, Instant.now(), user);
        entityManager.persist(comment);

        List<Comment> findComments = commentRepository.findByPost(post);

        assertThat(findComments).hasSize(1).contains(comment);

    }

    @Test
    @DisplayName("Should Found All Commnet By Post")
    public void shouldFoundAllCommentByPost() {
        User user =  new User(null, "User 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, user, Instant.now(), subreddit);
        entityManager.persist(post);
        Comment comment = new Comment(null, "Comment Content", post, Instant.now(), user);
        entityManager.persist(comment);
        Comment comment1 = new Comment(null, "Comment Content 1", post, Instant.now(), user);
        entityManager.persist(comment1);

        List<Comment> findComments = commentRepository.findAllByPost(post);

        assertThat(findComments).hasSize(2).contains(comment, comment1);
    }

    @Test
    @DisplayName("Should Found All Commnet By User")
    public void shouldFoundAllCommentByUser() {
        User user =  new User(null, "User 1", "secret password",
            "user1@email.com", Instant.now(), true);
        entityManager.persist(user);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, user, Instant.now(), subreddit);
        entityManager.persist(post);
        Comment comment = new Comment(null, "Comment Content", post, Instant.now(), user);
        entityManager.persist(comment);
        Comment comment1 = new Comment(null, "Comment Content 1", post, Instant.now(), user);
        entityManager.persist(comment1);

        List<Comment> findComments = commentRepository.findAllByUser(user);

        assertThat(findComments).hasSize(2).contains(comment, comment1);
    }
}
