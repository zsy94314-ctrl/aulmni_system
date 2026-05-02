package com.alumni.mapper;

import com.alumni.entity.DonationProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DonationProjectMapper extends BaseMapper<DonationProject> {
    @Update("UPDATE donation_project SET current_amount = current_amount + #{amount}, donor_count = donor_count + 1 WHERE id = #{id}")
    int updateAmount(@Param("id") Long id, @Param("amount") java.math.BigDecimal amount);
}
