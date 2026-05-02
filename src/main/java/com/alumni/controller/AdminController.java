package com.alumni.controller;

import com.alumni.entity.User;
import com.alumni.mapper.UserMapper;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public Result<?> listUsers() {
        List<User> list = userMapper.selectList(null);
        list.forEach(u -> u.setPassword(null));
        return Result.success(list);
    }
}
