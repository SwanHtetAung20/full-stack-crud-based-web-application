package com.crud_based_project.impls;

import com.crud_based_project.dtos.UserDto;
import com.crud_based_project.entities.ROLE;
import com.crud_based_project.entities.User;
import com.crud_based_project.exception.AppException;
import com.crud_based_project.repositories.UserRepository;
import com.crud_based_project.services.UserMapper;
import com.crud_based_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserImplementation implements UserService {

    private final UserRepository userRepository;
    // private final Cloudinary cloudinary;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found.!"));
            }
        };
    }

    @Override
    public List<UserDto> findAll() {
        Iterable<User> userList = userRepository.findAll();
        List<UserDto> filteredUserDtos = StreamSupport.stream(userList.spliterator(), false)
                .filter(user -> user.getRole() != null && user.getRole().equals(ROLE.USER))
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());

        return filteredUserDtos;
    }

    @Override
    public String delete(String id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return "Successfully Deleted.!";
    }

    @Transactional
    @Override
    public UserDto update(String id, UserDto userDto) {
        userRepository.findById(id).ifPresent(u -> {
            u.setName(userDto.getName());
            u.setEmail(userDto.getEmail());
            u.setPhone(userDto.getPhone());
            userRepository.save(u);
        });
        var user = userRepository.findById(id).orElseThrow();
        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Transactional
    @Override
    public UserDto upload(String id, MultipartFile file) {
        userRepository.findById(id).ifPresent(u -> {
            if (file.isEmpty()) {
                throw new AppException("file cannot be empty.!", HttpStatus.EXPECTATION_FAILED);
            }
            try {
                byte[] photoBytes = file.getBytes();
                String photo = Base64.getEncoder().encodeToString(photoBytes);
                u.setPhoto(photo);
                userRepository.save(u);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        var user = userRepository.findById(id).orElseThrow();
        return UserMapper.INSTANCE.userToUserDto(user);
    }


}
