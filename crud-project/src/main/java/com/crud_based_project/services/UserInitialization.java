package com.crud_based_project.services;

import com.crud_based_project.config.PasswordEncoderConfig;
import com.crud_based_project.entities.ROLE;
import com.crud_based_project.entities.User;
import com.crud_based_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInitialization {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;


    @Transactional
    @EventListener(classes = ContextRefreshedEvent.class)
    public void userInitializer() {
        if (userRepository.count() == 0) {
            var user = User.builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoderConfig.passwordEncoder().encode("admin@123"))
                    .isActive(true)
                    .role(ROLE.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
