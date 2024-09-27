//package com.crud_based_project;
//
//import com.crud_based_project.entities.ROLE;
//import com.crud_based_project.entities.User;
//import com.crud_based_project.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@RequiredArgsConstructor
//@SpringBootTest
//public class tutorial {
//
//    private final UserRepository userRepository;
//
//    @Transactional
//    @Test
//    public void testSaveUser() {
//        User user = User.builder()
//                .name("admin")
//                .email("admin@gmail.com")
//                .password("admin@123") // Use plaintext for testing
//                .phone("1234567890")
//                .isActive(true)
//                .role(ROLE.ADMIN)
//                .build();
//
//        try {
//           // userRepository.save(user);
//        } catch (Exception e) {
//            System.err.println("Error saving user: " + e.getMessage());
//            e.printStackTrace(); // This will give you more details about the error
//        }
//    }
//}
