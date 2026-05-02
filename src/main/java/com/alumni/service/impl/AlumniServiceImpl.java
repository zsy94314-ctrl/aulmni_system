package com.alumni.service.impl;

import com.alumni.dto.AlumniQueryDTO;
import com.alumni.entity.Alumni;
import com.alumni.entity.DuplicateRecord;
import com.alumni.mapper.AlumniMapper;
import com.alumni.mapper.ClazzMapper;
import com.alumni.mapper.DuplicateRecordMapper;
import com.alumni.service.AlumniService;
import com.alumni.utils.SimilarityUtil;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlumniServiceImpl implements AlumniService {
    @Autowired
    private AlumniMapper alumniMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private DuplicateRecordMapper duplicateRecordMapper;
    @Autowired
    private SimilarityUtil similarityUtil;

    @Override
    public Result<PageResult<Alumni>> list(AlumniQueryDTO query) {
        Page<Alumni> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<Alumni> result = alumniMapper.selectAlumniPage(page, query.getName(), query.getStudentNo(),
                query.getClassId(), query.getCollegeId(), query.getIndustry(), query.getRegion());
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize()));
    }

    @Override
    public Result<Alumni> getById(Long id) {
        return Result.success(alumniMapper.selectDetailById(id));
    }

    @Override
    public Result<?> save(Alumni alumni) {
        alumniMapper.insert(alumni);
        return Result.success();
    }

    @Override
    public Result<?> update(Alumni alumni) {
        alumniMapper.updateById(alumni);
        return Result.success();
    }

    @Override
    public Result<?> delete(Long id) {
        alumniMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> batchImport(List<Alumni> list) {
        for (Alumni a : list) {
            alumniMapper.insert(a);
        }
        return Result.success();
    }

    @Override
    public Result<?> findDuplicates() {
        List<Alumni> all = alumniMapper.selectList(new LambdaQueryWrapper<Alumni>().eq(Alumni::getDeleted, 0));
        List<DuplicateRecord> records = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            for (int j = i + 1; j < all.size(); j++) {
                double sim = similarityUtil.calculateSimilarity(all.get(i), all.get(j));
                if (sim >= 0.85) {
                    DuplicateRecord dr = new DuplicateRecord();
                    dr.setAlumniId1(all.get(i).getId());
                    dr.setAlumniId2(all.get(j).getId());
                    dr.setAlumniName1(all.get(i).getName());
                    dr.setAlumniName2(all.get(j).getName());
                    dr.setSimilarity(sim);
                    dr.setStatus(0);
                    records.add(dr);
                }
            }
        }
        if (!records.isEmpty()) {
            for (DuplicateRecord r : records) duplicateRecordMapper.insert(r);
        }
        return Result.success(records);
    }
}
