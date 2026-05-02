package com.alumni.service.impl;

import com.alumni.dto.LoginDTO;
import com.alumni.dto.RegisterDTO;
import com.alumni.entity.User;
import com.alumni.mapper.UserMapper;
import com.alumni.service.UserService;
import com.alumni.utils.JwtUtil;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<?> login(LoginDTO dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return Result.error(401, "用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            return Result.error(403, "账号已被禁用");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("refreshToken", refreshToken);
        data.put("user", user);
        return Result.success(data);
    }

    @Override
    public Result<?> register(RegisterDTO dto) {
        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            return Result.error("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole() != null ? dto.getRole() : "ALUMNI");
        user.setStatus(1);
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<?> getCurrentUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<?> updateUser(User user) {
        user.setPassword(null);
        userMapper.updateById(user);
        return Result.success();
    }
}
