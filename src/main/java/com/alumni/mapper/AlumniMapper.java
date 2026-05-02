package com.alumni.mapper;

import com.alumni.entity.Alumni;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface AlumniMapper extends BaseMapper<Alumni> {
    Page<Alumni> selectAlumniPage(Page<Alumni> page, @Param("name") String name, @Param("studentNo") String studentNo,
                                   @Param("classId") Long classId, @Param("collegeId") Long collegeId,
                                   @Param("industry") String industry, @Param("region") String region);

    @Select("SELECT a.*, c.name as class_name, col.name as college_name " +
            "FROM alumni a LEFT JOIN class c ON a.class_id = c.id " +
            "LEFT JOIN college col ON c.college_id = col.id " +
            "WHERE a.id = #{id} AND a.deleted = 0")
    Alumni selectDetailById(Long id);

    @Select("SELECT region as name, COUNT(*) as value FROM alumni WHERE deleted = 0 GROUP BY region ORDER BY value DESC LIMIT 10")
    List<Map<String, Object>> selectRegionStats();

    @Select("SELECT industry as name, COUNT(*) as value FROM alumni WHERE deleted = 0 AND industry IS NOT NULL GROUP BY industry ORDER BY value DESC LIMIT 10")
    List<Map<String, Object>> selectIndustryStats();

    @Select("SELECT COUNT(*) FROM alumni WHERE deleted = 0")
    Long selectTotalCount();
}
