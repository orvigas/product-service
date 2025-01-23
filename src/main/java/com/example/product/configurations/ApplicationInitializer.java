package com.example.product.configurations;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.product.models.User;
import com.example.product.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationInitializer {

    @Bean
    ApplicationRunner runner(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            User user = new User();
            user.setUsername("user@data.io");
            user.setPassword(encoder.encode("P@assword123.0"));
            var savedUser = userRepository.save(user);
            log.info("user: {}", savedUser);
        };
    }
}
