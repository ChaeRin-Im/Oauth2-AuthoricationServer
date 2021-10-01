package com.example.oauth2authoricationserver;

import com.example.oauth2authoricationserver.entity.User;
import com.example.oauth2authoricationserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUser(){
        userRepository.save(User.builder()
                .uid("user")
                .password(passwordEncoder.encode("pass"))
                .name("user")
                .email("skyer9@gmail.com")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

}
