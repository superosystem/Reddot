package com.gusrylmubarok.reddit.backend.service;


import com.gusrylmubarok.reddit.backend.dto.request.RegisterRequest;
import com.gusrylmubarok.reddit.backend.model.User;
import com.gusrylmubarok.reddit.backend.repository.AccountVerificationTokenRepository;
import com.gusrylmubarok.reddit.backend.repository.UserRepository;
import com.gusrylmubarok.reddit.backend.security.JwtProvider;
import com.gusrylmubarok.reddit.backend.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceTests {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountVerificationTokenRepository accountVerificationToken;
    @Mock
    private MailService mailService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private RefreshTokenService refreshTokenService;
    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    private User user;


    @Test
    @DisplayName("Should Success Register User")
    public void shouldSuccessRegisterUser() {
        // given
        RegisterRequest userRegister = new RegisterRequest("test1@gmail.com", "test1", "test1");

        String status = authServiceImpl.signup(userRegister);

        user = userRepository.findByEmail("test1@gmail.com");
        Assertions.assertEquals("Activation has been send", status);

    }

}
