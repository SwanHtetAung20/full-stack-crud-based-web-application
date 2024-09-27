package com.crud_based_project.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Objects;

public interface JWTService {

    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    String generateRefreshToken(HashMap<String, Objects> claimsResolver,UserDetails userDetails);

    boolean isTokenValid(String token,UserDetails userDetails);
}
