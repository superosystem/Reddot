package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Iterable<User> findByUsername(String username);
}