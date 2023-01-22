package com.reddot.repository

import com.reddot.data.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTests {
    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var eUser: User

    @BeforeEach
    fun tearUp() {
        this.eUser = User(1, "User 1", "user1", "user1@test.com",
            "user123456788", true, "USER", Date(), null)
    }

    @Test
    fun shouldCreateUser() {
        val act: User = userRepository.save(eUser)
        assertThat(act).isNotNull
        assertThat(act.username).isEqualTo(eUser.username)
    }

    @Test
    fun shouldGetByUsername() {
        userRepository.save(eUser)
        val act: Optional<User> = userRepository.findByUsername("user1")
        assertThat(act).isNotNull
    }
}