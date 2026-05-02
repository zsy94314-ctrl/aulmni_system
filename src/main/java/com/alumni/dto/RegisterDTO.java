package com.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String name;
    private String studentNo;
    private String email;
    private String phone;
    private String role;
}
