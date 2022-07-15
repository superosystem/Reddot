package com.gusrylmubarok.reddit.backend.repository;

import com.gusrylmubarok.reddit.backend.model.User;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

//    @BeforeEach
    void initUseCase() {
        List<User> users = Arrays.asList(
                new User(
                        "test_name", "test_password", "test@gmail.com", Instant.now(), true
                )
        );
        userRepository.saveAll(users);
    }

    @AfterEach
    public void destroyAll() {
        userRepository.deleteAll();
    }

    @Test
    void should_store_a_user() {
        User user = userRepository.save(new User(
           "test1", "passwd1", "test@gmail.com", Instant.now(), true
        ));

        assertThat(user).hasFieldOrPropertyWithValue("username", "test1");
        assertThat(user).hasFieldOrPropertyWithValue("password", "passwd1");
        assertThat(user).hasFieldOrPropertyWithValue("email", "test@gmail.com");
    }

    @Test
    void should_find_all_users() {
        User user1 = new User("test1", "passwd1", "test1@gmail.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User("test2", "passwd2", "test2@gmail.com", Instant.now(), true);
        entityManager.persist(user2);
        User user3 = new User("test3", "passwd3", "test3@gmail.com", Instant.now(), true);
        entityManager.persist(user3);

        List<User> usersExpected = userRepository.findAll();
        assertThat(usersExpected).hasSize(3).contains(user1, user2, user3);
    }

    @Test
    void should_find_user_by_id() {
        User user1 = new User("test1", "passwd1", "test1@gmail.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User("test2", "passwd2", "test2@gmail.com", Instant.now(), true);
        entityManager.persist(user2);

        User foundUser = userRepository.findById(user1.getId()).get();
        assertThat(foundUser).isEqualTo(user1);
    }

    @Test
    void should_delete_user_by_id() {
        User user1 = new User("test1", "passwd1", "test1@gmail.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User("test2", "passwd2", "test2@gmail.com", Instant.now(), true);
        entityManager.persist(user2);

        userRepository.deleteById(user1.getId());
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(1).contains(user2);
    }

    @Test
    void should_find_user_by_username() {
        User user1 = new User("test1", "passwd1", "test1@gmail.com", Instant.now(), true);
        entityManager.persist(user1);
        User user2 = new User("test1", "passwd2", "test2@gmail.com", Instant.now(), true);
        entityManager.persist(user2);

        Iterable<User> foundUser = userRepository.findByUsername("test1");

        assertThat(foundUser).hasSize(2).contains(user1, user2);

    }

}
