package com.alumni.mapper;

import com.alumni.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Map;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    @Update("UPDATE activity SET registered_count = registered_count + #{delta} WHERE id = #{id}")
    int updateRegisteredCount(@Param("id") Long id, @Param("delta") int delta);

    @Select("SELECT type as name, COUNT(*) as value FROM activity WHERE deleted = 0 GROUP BY type")
    List<Map<String, Object>> selectTypeStats();
}
