package com.gusrylmubarok.reddit.backend.controller;

import com.gusrylmubarok.reddit.backend.dto.response.AuthenticationResponse;
import com.gusrylmubarok.reddit.backend.dto.request.LoginRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RefreshTokenRequest;
import com.gusrylmubarok.reddit.backend.dto.request.RegisterRequest;
import com.gusrylmubarok.reddit.backend.exceptions.ActivationException;
import com.gusrylmubarok.reddit.backend.service.AuthService;
import com.gusrylmubarok.reddit.backend.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.signup(registerRequest);
        }catch(ActivationException e) {
            return new ResponseEntity<>("Email has already used", OK);
        }
        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
