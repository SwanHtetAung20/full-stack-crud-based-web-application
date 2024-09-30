package com.crud_based_project.services;

import com.crud_based_project.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    List<UserDto> findAll();

    String delete(String id);

    UserDto update(String id, UserDto userDto);

    UserDto upload(String id, MultipartFile file);
}
