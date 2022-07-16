package com.gusrylmubarok.reddit.backend.controller;

import com.gusrylmubarok.reddit.backend.dto.AuthenticationResponseDTO;
import com.gusrylmubarok.reddit.backend.dto.LoginRequestDTO;
import com.gusrylmubarok.reddit.backend.dto.RegisterRequestDTO;
import com.gusrylmubarok.reddit.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDTO registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
}
