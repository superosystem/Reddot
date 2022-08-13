package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.dto.response.AuthenticationResponse;
import com.gusrylmubarok.reddit.backend.dto.request.LoginRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RefreshTokenRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RegisterRequest;
import com.gusrylmubarok.reddit.backend.model.AccountVerificationToken;
import com.gusrylmubarok.reddit.backend.model.User;

public interface AuthService {
    String signup(RegisterRequest registerRequest);
    AuthenticationResponse login(LoginRequest loginRequest);
    User getCurrentUser();
    void enableAccount(AccountVerificationToken token);
    String generateVerificationToken(User user);
    void verifyAccount(String token);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    boolean isLoggedIn();
}
