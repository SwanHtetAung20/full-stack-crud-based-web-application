package com.crud_based_project.controllers;

import com.crud_based_project.dtos.RefreshToken;
import com.crud_based_project.dtos.SignUpOrSignInDto;
import com.crud_based_project.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SignUpOrSignInDto> loginHandler(@RequestBody SignUpOrSignInDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(dto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpHandler(@RequestBody SignUpOrSignInDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signUp(dto));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<RefreshToken> refreshTokenHandler(@RequestBody RefreshToken token) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getRefreshToken(token));
    }
}
