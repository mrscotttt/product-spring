package com.example.productspring.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User {

    private Long id;

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "username must not be blank")
    private String username;

    @NotBlank(message = "email must not be blank")
    private String email;

    private String phone;
    private String website;
}
