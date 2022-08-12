package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should No User")
    public void shouldFindNoUserIfRepositoryIsEmpty() {
        Iterable users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Should Save User")
    public void souldStoreAUser() {
        User user = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("userId").isEqualTo(user);
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", "test 1");
    }

    @Test
    @DisplayName("#User Should Find All")
    public void shouldFindAllUser() {
        User user1 = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User(null, "test 2", "secret password",
                "user2@email.com", Instant.now(), true);
        entityManager.persist(user2);

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2).contains(user1, user2);
    }

    @Test
    @DisplayName("#User Should updated")
    public void shouldUpdateUser() {
        User user1 = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user1);
        User updateUser = new User(null, "test 1 Update", "secret password",
                "user1@email.com", user1.getCreationDate(), true);

        User user = userRepository.findById(user1.getUserId()).get();
        user.setUsername(updateUser.getUsername());
        user.setPassword(updateUser.getPassword());
        user.setEmail(updateUser.getEmail());
        user.setCreationDate(updateUser.getCreationDate());
        user.setAccountStatus(updateUser.isAccountStatus());
        userRepository.save(user);
        User checkUser = userRepository.findById(user1.getUserId()).get();

        assertThat(checkUser.getUserId()).isEqualTo(user1.getUserId());
        assertThat(checkUser.getUsername()).isEqualTo(updateUser.getUsername());
    }

    @Test
    @DisplayName("#User Should deleted")
    public void shouldDeleteUserById() {
        User user1 = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User(null, "test 2", "secret password",
                "user2@email.com", Instant.now(), true);
        entityManager.persist(user2);

        userRepository.deleteById(user1.getUserId());
        Iterable users = userRepository.findAll();

        assertThat(users).hasSize(1).contains(user2);
    }

    @Test
    @DisplayName("#User Should Find By Username")
    public void shouldFindUserByUsername() {
        User user1 = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User(null, "test 2", "secret password",
                "user2@email.com", Instant.now(), true);
        entityManager.persist(user2);

        Optional<User> userFound = userRepository.findByUsername(user1.getUsername());

        assertThat(userFound).contains(user1);

    }


    @Test
    @DisplayName("#User Should Find By Id")
    public void shouldFindUserById() {
        User user1 = new User(null, "test 1", "secret password",
                "user1@email.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User(null, "test 2", "secret password",
                "user2@email.com", Instant.now(), true);
        entityManager.persist(user2);

        User userFound = userRepository.findById(user1.getUserId()).get();

        assertThat(userFound).isEqualTo(user1);
    }

}
