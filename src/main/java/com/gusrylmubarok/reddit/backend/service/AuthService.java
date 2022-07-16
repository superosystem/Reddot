package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.RegisterRequestDTO;
import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;
import com.gusrylmubarok.reddit.backend.model.NotificationEmail;
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
import java.util.Optional;
import java.util.UUID;

import static com.gusrylmubarok.reddit.backend.util.Constants.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

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

        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);
        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
    }
    // Verification Account
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new BackendRedditException("Invalid token!"));

    }
    // fetch User enabled
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUserId().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new BackendRedditException("User not found id - " + username));
        user.setEnabled(true);

        userRepository.save(user);
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
    // Encode Password Method
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
