package com.gusrylmubarok.reddit.backend.service.impl;

import com.gusrylmubarok.reddit.backend.dto.request.LoginRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RefreshTokenRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RegisterRequest;
import com.gusrylmubarok.reddit.backend.dto.response.AuthenticationResponse;
import com.gusrylmubarok.reddit.backend.exceptions.ActivationException;
import com.gusrylmubarok.reddit.backend.model.AccountVerificationToken;
import com.gusrylmubarok.reddit.backend.model.NotificationEmail;
import com.gusrylmubarok.reddit.backend.model.User;
import com.gusrylmubarok.reddit.backend.repository.AccountVerificationTokenRepository;
import com.gusrylmubarok.reddit.backend.repository.UserRepository;
import com.gusrylmubarok.reddit.backend.security.JwtProvider;
import com.gusrylmubarok.reddit.backend.service.AuthService;
import com.gusrylmubarok.reddit.backend.service.MailService;
import com.gusrylmubarok.reddit.backend.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountVerificationTokenRepository accountVerificationToken;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public String signup(RegisterRequest registerRequest) {
        User userExists = userRepository.findByEmail(registerRequest.getEmail());
        if (userExists == null) {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreationDate(Instant.now());
            user.setAccountStatus(false);

            userRepository.save(user);

            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/v1/auth/accountVerification/" + token));
            return "Activation has been send";
        }else {
            if(isActiveUser(userExists.getEmail()) == true) {
                AccountVerificationToken tokenUser = accountVerificationToken.findByUser(userExists);
                String token = tokenUser.getToken();
                mailService.sendMail(new NotificationEmail("Please Activate your Account",
                        userExists.getEmail(), "Thank you for signing up to Spring Reddit, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/v1/auth/accountVerification/" + token));
                return "Activation has been send again";
            }else {
                throw new ActivationException("Email has already used");
            }
        }
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    @Override
    public void enableAccount(AccountVerificationToken token) {
        String username = token.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ActivationException("User not found with username: " + username));
        user.setAccountStatus(true);
        userRepository.save(user);
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        accountVerificationToken.setToken(token);
        accountVerificationToken.setUser(user);

        this.accountVerificationToken.save(accountVerificationToken);
        return token;
    }

    @Override
    public void verifyAccount(String token) {
        Optional<AccountVerificationToken> verificationToken = accountVerificationToken.findByToken(token);
        verificationToken.orElseThrow(() -> new ActivationException("Invalid Activation Token"));
        enableAccount(verificationToken.get());
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    private boolean isActiveUser(String email) {
        User user = userRepository.findByEmail(email);
        return user.isAccountStatus() == true ? true : false;
    }
}
