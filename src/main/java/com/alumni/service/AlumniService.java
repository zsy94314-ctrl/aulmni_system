package com.alumni.service;

import com.alumni.dto.AlumniQueryDTO;
import com.alumni.entity.Alumni;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;

import java.util.List;

public interface AlumniService {
    Result<PageResult<Alumni>> list(AlumniQueryDTO query);
    Result<Alumni> getById(Long id);
    Result<?> save(Alumni alumni);
    Result<?> update(Alumni alumni);
    Result<?> delete(Long id);
    Result<?> batchImport(List<Alumni> list);
    Result<?> findDuplicates();
}
