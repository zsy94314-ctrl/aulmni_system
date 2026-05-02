package com.alumni.mapper;

import com.alumni.entity.Clazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {
    @Select("SELECT c.*, col.name as college_name FROM class c LEFT JOIN college col ON c.college_id = col.id WHERE c.deleted = 0")
    List<Clazz> selectAllWithCollege();
}
