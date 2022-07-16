package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.RegisterRequestDTO;
import com.gusrylmubarok.reddit.backend.model.User;
import com.gusrylmubarok.reddit.backend.model.VerificationToken;
import com.gusrylmubarok.reddit.backend.repository.UserRepository;
import com.gusrylmubarok.reddit.backend.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequestDTO registerRequestDTO) {
        User user  = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(encodePassword(registerRequestDTO.getPassword()));
        user.setEmail(registerRequestDTO.getEmail());
        user.setCreatedAt(Instant.now());
        // Set enabled to false for authentication via email;
        user.setEnabled(false);

        userRepository.save(user);

        String token = generatedVerificationToken(user);
    }

    // Encode Password Method
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Generate Token for verification that will send via email
    private String generatedVerificationToken(User user) {
        String randomToken = UUID.randomUUID().toString();
        // Save token into database
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(randomToken);
        verificationToken.setUserId(user);
        verificationTokenRepository.save(verificationToken);

        return randomToken;
    }

}
