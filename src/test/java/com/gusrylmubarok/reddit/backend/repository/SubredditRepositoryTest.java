package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.Subreddit;
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
public class SubredditRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SubredditRepository subredditRepository;

    @Test
    @DisplayName("Should find Subreddit Equal Name")
    public void shoudlFindSubredditByName() {
//        User user =  new User(null, "User 1", "secret password",
//                "user1@email.com", Instant.now(), true);
//        List<Post> posts = new ArrayList<>();
//        posts.add(new Post(null, "Post 1", "https://postlink.com", "Post Description",
//                0, user, Instant.now(), null));
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);

        Optional<Subreddit> findSubreddit = subredditRepository.findByNameEquals(subreddit.getName());

        assertThat(findSubreddit).contains(subreddit);
    }

}
