package com.gusrylmubarok.reddit.backend.repository;

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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PostRepository postRepository;


    @Test
    @DisplayName("Should Find Post By Subreddit")
    public void shouldFoundPostBySubreddit() {
        User user =  new User(null, "User 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, user, Instant.now(), subreddit);
        entityManager.persist(post);

        Optional<Post> findSubreddit = postRepository.findAllBySubreddit(subreddit);

        assertThat(findSubreddit).contains(post);
    }

    @Test
    @DisplayName("Should Find Post By User")
    public void shouldFoundPostByUser() {
        User user =  new User(null, "User 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, user, Instant.now(), subreddit);
        entityManager.persist(post);

        Optional<Post> findSubreddit = postRepository.findByUser(user);

        assertThat(findSubreddit).contains(post);
    }
}
