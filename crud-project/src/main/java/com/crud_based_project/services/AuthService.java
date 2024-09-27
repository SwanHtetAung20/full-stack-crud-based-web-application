package com.crud_based_project.services;

import com.crud_based_project.dtos.RefreshToken;
import com.crud_based_project.dtos.SignUpOrSignInDto;

public interface AuthService {

    SignUpOrSignInDto login(SignUpOrSignInDto dto);

    String signUp(SignUpOrSignInDto dto);

    RefreshToken getRefreshToken(RefreshToken refreshToken);
}
