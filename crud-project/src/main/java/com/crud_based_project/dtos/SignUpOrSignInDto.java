package com.crud_based_project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpOrSignInDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String isActive;
    private String photo;
    private MultipartFile file;
    private String token;
    private String refreshToken;
}
