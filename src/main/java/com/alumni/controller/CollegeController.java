package com.alumni.controller;

import com.alumni.entity.College;
import com.alumni.mapper.CollegeMapper;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {
    @Autowired
    private CollegeMapper collegeMapper;

    @GetMapping
    public Result<?> list() {
        List<College> list = collegeMapper.selectList(null);
        return Result.success(list);
    }

    @PostMapping
    public Result<?> save(@RequestBody College college) {
        collegeMapper.insert(college);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody College college) {
        college.setId(id);
        collegeMapper.updateById(college);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        collegeMapper.deleteById(id);
        return Result.success();
    }
}
