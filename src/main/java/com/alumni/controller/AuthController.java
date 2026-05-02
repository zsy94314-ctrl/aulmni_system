package com.alumni.controller;

import com.alumni.dto.LoginDTO;
import com.alumni.dto.RegisterDTO;
import com.alumni.entity.User;
import com.alumni.security.SecurityUser;
import com.alumni.service.UserService;
import com.alumni.utils.JwtUtil;
import com.alumni.vo.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @GetMapping("/me")
    public Result<?> me(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return userService.getCurrentUser(user.getId());
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user, Authentication authentication) {
        SecurityUser su = (SecurityUser) authentication.getPrincipal();
        user.setId(su.getId());
        return userService.updateUser(user);
    }
}
