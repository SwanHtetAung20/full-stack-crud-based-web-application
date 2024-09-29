package com.crud_based_project.services;

import com.crud_based_project.dtos.UserDto;
import com.crud_based_project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);
}
