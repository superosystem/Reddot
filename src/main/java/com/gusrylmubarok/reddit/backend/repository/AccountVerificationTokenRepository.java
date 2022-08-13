package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.AccountVerificationToken;
import com.gusrylmubarok.reddit.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountVerificationTokenRepository extends JpaRepository<AccountVerificationToken, Long> {
    Optional<AccountVerificationToken> findByToken(String token);
    AccountVerificationToken findByUser(User user);
}
