package com.alumni.controller;

import com.alumni.dto.NewsDTO;
import com.alumni.service.NewsService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    public Result<?> list(@RequestParam(required = false) String category,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return newsService.list(category, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @PostMapping
    public Result<?> save(@RequestBody NewsDTO dto) {
        return newsService.save(dto);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody NewsDTO dto) {
        dto.setId(id);
        return newsService.update(dto);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        return newsService.delete(id);
    }
}
