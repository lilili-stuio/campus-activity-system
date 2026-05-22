package com.campus.activity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String studentId;

    @NotBlank
    private String password;
}
