package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.Post;
import com.gusrylmubarok.reddit.backend.model.Subreddit;
import com.gusrylmubarok.reddit.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findAllBySubreddit(Subreddit subreddit);
    Optional<Post> findByUser(User user);
}
