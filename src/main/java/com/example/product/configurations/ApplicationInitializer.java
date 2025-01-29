package com.example.product.configurations;

import java.util.stream.Collectors;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.product.dtos.UserDto;
import com.example.product.models.User;
import com.example.product.repositories.RoleRepository;
import com.example.product.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
class ApplicationInitializer {

    @Bean
    ApplicationRunner runner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
            ObjectMapper objectMapper) {
        return args -> {
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setUsername("user@data.io");
            user.setPassword(encoder.encode("P@assword123.0"));
            user.setRoles(roleRepository.findAll().stream().collect(Collectors.toSet()));

            var savedUser = UserDto.from(userRepository.save(user));
            log.info("user: {}", objectMapper.writeValueAsString(savedUser));
        };
    }
}
