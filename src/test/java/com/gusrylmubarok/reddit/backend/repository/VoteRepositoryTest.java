package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.*;
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
public class VoteRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VoteRepository voteRepository;


    @Test
    @DisplayName("Should Find Top By Post And User Order By Vote")
    public void shouldFindTopVote() {
        User currentUser =  new User(null, "User 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(currentUser);
        Subreddit subreddit = new Subreddit(null, "First Subreddit", "Description",
                null, Instant.now(), null);
        entityManager.persist(subreddit);
        Post post = new Post(null, "Post 1", "https://postlink.com", "Post Description",
                0, currentUser, Instant.now(), subreddit);
        entityManager.persist(post);
        Vote vote = new Vote(null, VoteType.UPVOTE, post, currentUser);
        entityManager.persist(vote);

        Optional<Vote> votes = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        assertThat(votes).hasValue(vote);
    }
}
