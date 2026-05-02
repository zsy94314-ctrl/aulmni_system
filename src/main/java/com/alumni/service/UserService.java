package com.alumni.service;

import com.alumni.dto.LoginDTO;
import com.alumni.dto.RegisterDTO;
import com.alumni.entity.User;
import com.alumni.vo.Result;

public interface UserService {
    Result<?> login(LoginDTO dto);
    Result<?> register(RegisterDTO dto);
    Result<?> getCurrentUser(Long userId);
    Result<?> updateUser(User user);
}
