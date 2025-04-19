package com.example.product.dtos;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.example.product.models.Role;
import com.example.product.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto implements Serializable {

    private long id;
    @Size(min = 2, max = 255)
    @NotBlank(message = "The field \"firstName\" is mandatory")
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$", message = "The field \"username\" must be a valid email")
    @NotBlank(message = "The field \"username\" is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "The field \"password\" must be a valid password")
    @NotBlank(message = "The field \"password\" is mandatory")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private Set<String> roles;

    public static UserDto from(final User user) {
        if (user == null) {
            return null;
        }

        final var instance = new UserDto();

        BeanUtils.copyProperties(user, instance, "roles");
        instance.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return instance;
    }

}
