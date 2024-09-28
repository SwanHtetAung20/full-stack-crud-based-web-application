package com.crud_based_project.impls;

import com.crud_based_project.config.PasswordEncoderConfig;
import com.crud_based_project.dtos.RefreshToken;
import com.crud_based_project.dtos.SignUpOrSignInDto;
import com.crud_based_project.entities.ROLE;
import com.crud_based_project.entities.User;
import com.crud_based_project.repositories.UserRepository;
import com.crud_based_project.services.AuthService;
import com.crud_based_project.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @Override
    public SignUpOrSignInDto login(SignUpOrSignInDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        var user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found.!"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return SignUpOrSignInDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .build();
    }

    @Override
    public String signUp(SignUpOrSignInDto dto) {
        var user = User
                .builder()
                .email(dto.getEmail())
                .password(passwordEncoderConfig.passwordEncoder().encode(dto.getPassword()))
                .role(ROLE.USER)
                .isActive(true)
                .build();
        userRepository.save(user);
        return "Successfully Sign up. Please login.!";
    }

    @Override
    public RefreshToken getRefreshToken(RefreshToken refreshToken) {
        var userEmail = jwtService.extractUserName(refreshToken.getRefreshToken());
        var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found.!"));
        if (jwtService.isTokenValid(refreshToken.getRefreshToken(), user)) {
            var token = jwtService.generateToken(user);
            return RefreshToken.builder()
                    .refreshToken(token)
                    .build();
        }
        return null;
    }
}
