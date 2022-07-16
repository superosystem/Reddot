package com.gusrylmubarok.reddit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String authenticationToken;
    private String username;
}
