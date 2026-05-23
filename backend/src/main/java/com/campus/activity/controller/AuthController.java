package com.campus.activity.controller;

import com.campus.activity.dto.ApiResult;
import com.campus.activity.dto.LoginRequest;
import com.campus.activity.dto.LoginResponse;
import com.campus.activity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResult<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResult.success(userService.login(request));
    }

    @GetMapping("/me")
    public ApiResult<String> me(@RequestAttribute("studentId") String studentId,
                                 @RequestAttribute("role") String role) {
        return ApiResult.success("当前用户: " + studentId + " 角色: " + role);
    }
}
