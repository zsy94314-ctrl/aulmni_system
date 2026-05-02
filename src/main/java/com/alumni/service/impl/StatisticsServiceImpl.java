package com.alumni.service.impl;

import com.alumni.entity.Alumni;
import com.alumni.entity.Clazz;
import com.alumni.mapper.AlumniMapper;
import com.alumni.mapper.ClazzMapper;
import com.alumni.mapper.DonationMapper;
import com.alumni.service.StatisticsService;
import com.alumni.vo.GraphDataVO;
import com.alumni.vo.GraphEdgeVO;
import com.alumni.vo.GraphNodeVO;
import com.alumni.vo.Result;
import com.alumni.vo.StatisticsVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private AlumniMapper alumniMapper;
    @Autowired
    private DonationMapper donationMapper;
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public Result<StatisticsVO> getStatistics() {
        StatisticsVO vo = new StatisticsVO();
        vo.setTotalAlumni(alumniMapper.selectTotalCount());
        vo.setTotalActivities(0L);
        vo.setTotalDonations(0L);
        vo.setTotalDonationAmount(new java.math.BigDecimal("0"));
        vo.setAlumniByRegion(alumniMapper.selectRegionStats());
        vo.setAlumniByIndustry(alumniMapper.selectIndustryStats());
        vo.setDonationTrend(donationMapper.selectMonthlyTrend());
        vo.setTopDonors(donationMapper.selectTopDonors());
        return Result.success(vo);
    }

    @Override
    public Result<GraphDataVO> getRelationshipGraph(String type) {
        List<Alumni> alumniList = alumniMapper.selectList(new LambdaQueryWrapper<>());
        List<Clazz> clazzList = clazzMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, Long> alumniClassMap = alumniList.stream()
                .filter(a -> a.getClassId() != null)
                .collect(Collectors.toMap(Alumni::getId, Alumni::getClassId, (a, b) -> a));
        Map<Long, Long> classCollegeMap = clazzList.stream()
                .filter(c -> c.getCollegeId() != null)
                .collect(Collectors.toMap(Clazz::getId, Clazz::getCollegeId, (a, b) -> a));

        List<GraphNodeVO> nodes = new ArrayList<>();
        List<GraphEdgeVO> edges = new ArrayList<>();
        Map<Long, Integer> nodeIndexMap = new HashMap<>();

        for (int i = 0; i < alumniList.size(); i++) {
            Alumni a = alumniList.get(i);
            GraphNodeVO node = new GraphNodeVO();
            node.setId(a.getId());
            node.setName(a.getName());
            node.setCategory(a.getIndustry() != null ? a.getIndustry() : "未设置");
            node.setValue(20);
            nodes.add(node);
            nodeIndexMap.put(a.getId(), i);
        }

        for (int i = 0; i < alumniList.size(); i++) {
            for (int j = i + 1; j < alumniList.size(); j++) {
                Alumni a1 = alumniList.get(i);
                Alumni a2 = alumniList.get(j);
                String relation = null;
                double weight = 0;
                if ("classmate".equals(type) || "all".equals(type)) {
                    if (a1.getClassId() != null && a1.getClassId().equals(a2.getClassId())) {
                        relation = "同班同学";
                        weight = 1.0;
                    }
                }
                if ("colleague".equals(type) || "all".equals(type)) {
                    Long c1 = classCollegeMap.get(a1.getClassId());
                    Long c2 = classCollegeMap.get(a2.getClassId());
                    if (c1 != null && c1.equals(c2) && relation == null) {
                        relation = "同院校友";
                        weight = 0.7;
                    }
                }
                if ("regional".equals(type) || "all".equals(type)) {
                    if (a1.getRegion() != null && a1.getRegion().equals(a2.getRegion()) && relation == null) {
                        relation = "同地域";
                        weight = 0.5;
                    }
                }
                if ("industrial".equals(type) || "all".equals(type)) {
                    if (a1.getIndustry() != null && a1.getIndustry().equals(a2.getIndustry()) && relation == null) {
                        relation = "同行业";
                        weight = 0.4;
                    }
                }
                if (relation != null) {
                    GraphEdgeVO edge = new GraphEdgeVO();
                    edge.setSource(a1.getId());
                    edge.setTarget(a2.getId());
                    edge.setRelation(relation);
                    edge.setWeight(weight);
                    edges.add(edge);
                }
            }
        }

        GraphDataVO data = new GraphDataVO();
        data.setNodes(nodes);
        data.setEdges(edges);
        return Result.success(data);
    }
}
