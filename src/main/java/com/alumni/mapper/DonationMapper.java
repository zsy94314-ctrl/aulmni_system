package com.alumni.mapper;

import com.alumni.entity.Donation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface DonationMapper extends BaseMapper<Donation> {
    @Select("SELECT d.*, p.title as project_title, a.name as alumni_name FROM donation d " +
            "LEFT JOIN donation_project p ON d.project_id = p.id " +
            "LEFT JOIN alumni a ON d.alumni_id = a.id WHERE d.alumni_id = #{alumniId} ORDER BY d.create_time DESC")
    List<Donation> selectByAlumniId(Long alumniId);

    @Select("SELECT d.*, p.title as project_title, a.name as alumni_name FROM donation d " +
            "LEFT JOIN donation_project p ON d.project_id = p.id " +
            "LEFT JOIN alumni a ON d.alumni_id = a.id WHERE d.project_id = #{projectId} ORDER BY d.create_time DESC")
    List<Donation> selectByProjectId(Long projectId);

    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') as month, SUM(amount) as amount FROM donation " +
            "WHERE status = 1 GROUP BY month ORDER BY month DESC LIMIT 12")
    List<Map<String, Object>> selectMonthlyTrend();

    @Select("SELECT a.name as name, SUM(d.amount) as amount FROM donation d " +
            "LEFT JOIN alumni a ON d.alumni_id = a.id WHERE d.status = 1 GROUP BY d.alumni_id ORDER BY amount DESC LIMIT 10")
    List<Map<String, Object>> selectTopDonors();
}
