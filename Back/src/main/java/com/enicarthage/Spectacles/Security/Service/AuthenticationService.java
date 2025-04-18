package com.enicarthage.Spectacles.Security.Service;

import com.enicarthage.Spectacles.Security.DTOs.AuthenticationRequest;
import com.enicarthage.Spectacles.Security.DTOs.AuthenticationResponse;
import com.enicarthage.Spectacles.Security.DTOs.RegisterRequest;
import com.enicarthage.Spectacles.Security.JwtUtil;
import com.enicarthage.Spectacles.User.Model.User;
import com.enicarthage.Spectacles.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .tel(request.getTel())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .build();

        userRepository.save(user);

        var jwtToken = jwtUtil.generateToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authentifier l'utilisateur avec les informations de connexion
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        // Trouver l'utilisateur dans la base de données
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Générer le token JWT pour l'utilisateur
        var jwtToken = jwtUtil.generateToken(user.getEmail());

        // Retourner la réponse avec le token et l'ID de l'utilisateur
        return AuthenticationResponse.builder()
                .token(jwtToken)  // Inclure le token
                .id(user.getId())  // Inclure l'ID de l'utilisateur
                .build();
    }

}
