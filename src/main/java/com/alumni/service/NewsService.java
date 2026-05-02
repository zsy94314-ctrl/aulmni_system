package com.alumni.service;

import com.alumni.dto.NewsDTO;
import com.alumni.entity.News;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;

public interface NewsService {
    Result<PageResult<News>> list(String category, Integer pageNum, Integer pageSize);
    Result<News> getById(Long id);
    Result<?> save(NewsDTO dto);
    Result<?> update(NewsDTO dto);
    Result<?> delete(Long id);
}
