package com.alumni.service.impl;

import com.alumni.dto.NewsDTO;
import com.alumni.entity.News;
import com.alumni.mapper.NewsMapper;
import com.alumni.service.NewsService;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Result<PageResult<News>> list(String category, Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> qw = new LambdaQueryWrapper<>();
        if (category != null && !category.isEmpty()) qw.eq(News::getCategory, category);
        qw.eq(News::getDeleted, 0).eq(News::getStatus, 1).orderByDesc(News::getCreateTime);
        Page<News> result = newsMapper.selectPage(page, qw);
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize()));
    }

    @Override
    public Result<News> getById(Long id) {
        newsMapper.incrementViewCount(id);
        return Result.success(newsMapper.selectById(id));
    }

    @Override
    public Result<?> save(NewsDTO dto) {
        News n = new News();
        BeanUtils.copyProperties(dto, n);
        n.setViewCount(0);
        newsMapper.insert(n);
        return Result.success();
    }

    @Override
    public Result<?> update(NewsDTO dto) {
        News n = new News();
        BeanUtils.copyProperties(dto, n);
        newsMapper.updateById(n);
        return Result.success();
    }

    @Override
    public Result<?> delete(Long id) {
        newsMapper.deleteById(id);
        return Result.success();
    }
}
