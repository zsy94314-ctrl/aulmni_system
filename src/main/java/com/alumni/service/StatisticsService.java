package com.alumni.service;

import com.alumni.vo.GraphDataVO;
import com.alumni.vo.Result;
import com.alumni.vo.StatisticsVO;

public interface StatisticsService {
    Result<StatisticsVO> getStatistics();
    Result<GraphDataVO> getRelationshipGraph(String type);
}
