package com.campus.activity.service;

import com.campus.activity.config.JwtUtil;
import com.campus.activity.dto.LoginRequest;
import com.campus.activity.dto.LoginResponse;
import com.campus.activity.entity.User;
import com.campus.activity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> opt = userRepository.findByStudentId(request.getStudentId());
        if (opt.isEmpty()) {
            throw new RuntimeException("学号不存在");
        }
        User user = opt.get();
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getStudentId(), user.getRole());
        return new LoginResponse(token, user.getName(), user.getRole(), user.getStudentId());
    }
}
