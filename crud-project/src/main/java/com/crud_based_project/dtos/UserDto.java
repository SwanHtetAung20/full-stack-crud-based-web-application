package com.crud_based_project.dtos;

import com.crud_based_project.entities.ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String photo;
    private ROLE role;
    private MultipartFile file;
}
