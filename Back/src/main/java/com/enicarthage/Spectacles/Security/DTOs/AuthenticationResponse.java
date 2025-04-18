package com.enicarthage.Spectacles.Security.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;  // Le token JWT
    private Long id;       // L'ID de l'utilisateur (client)
}
