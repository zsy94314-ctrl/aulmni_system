package com.alumni.controller;

import com.alumni.entity.Clazz;
import com.alumni.mapper.ClazzMapper;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    @Autowired
    private ClazzMapper clazzMapper;

    @GetMapping
    public Result<?> list() {
        List<Clazz> list = clazzMapper.selectAllWithCollege();
        return Result.success(list);
    }

    @PostMapping
    public Result<?> save(@RequestBody Clazz clazz) {
        clazzMapper.insert(clazz);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Clazz clazz) {
        clazz.setId(id);
        clazzMapper.updateById(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        clazzMapper.deleteById(id);
        return Result.success();
    }
}
