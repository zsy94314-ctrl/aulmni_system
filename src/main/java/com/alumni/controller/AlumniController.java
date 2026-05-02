package com.alumni.controller;

import com.alumni.dto.AlumniQueryDTO;
import com.alumni.entity.Alumni;
import com.alumni.service.AlumniService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {
    @Autowired
    private AlumniService alumniService;

    @GetMapping
    public Result<?> list(AlumniQueryDTO query) {
        return alumniService.list(query);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return alumniService.getById(id);
    }

    @PostMapping
    public Result<?> save(@RequestBody Alumni alumni) {
        return alumniService.save(alumni);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Alumni alumni) {
        alumni.setId(id);
        return alumniService.update(alumni);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        return alumniService.delete(id);
    }

    @PostMapping("/batch")
    public Result<?> batchImport(@RequestBody List<Alumni> list) {
        return alumniService.batchImport(list);
    }

    @PostMapping("/duplicates/find")
    public Result<?> findDuplicates() {
        return alumniService.findDuplicates();
    }
}
