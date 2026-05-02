package com.alumni.mapper;

import com.alumni.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
    @Update("UPDATE news SET view_count = view_count + 1 WHERE id = #{id}")
    int incrementViewCount(Long id);
}
