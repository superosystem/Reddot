package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.Subreddit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends PagingAndSortingRepository<Subreddit, Long> {
    Optional<Subreddit> findByNameEquals(String subredditName);
    Optional<Page<Subreddit>> findByNameLike(String subredditName, Pageable pageable);
}
