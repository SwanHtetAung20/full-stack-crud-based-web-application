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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserImplementation implements UserService {

    private final UserRepository userRepository;

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
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return "Successfully Deleted.!";
    }
}
