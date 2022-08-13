package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    void deleteRefreshToken(String token);
}
