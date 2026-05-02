package com.alumni.controller;

import com.alumni.service.StatisticsService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public Result<?> getStatistics() {
        return statisticsService.getStatistics();
    }

    @GetMapping("/graph")
    public Result<?> getGraph(@RequestParam(defaultValue = "all") String type) {
        return statisticsService.getRelationshipGraph(type);
    }
}
