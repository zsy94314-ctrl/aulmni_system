package com.alumni.service.impl;

import com.alumni.dto.DonationDTO;
import com.alumni.dto.DonationProjectDTO;
import com.alumni.entity.Donation;
import com.alumni.entity.DonationProject;
import com.alumni.mapper.DonationMapper;
import com.alumni.mapper.DonationProjectMapper;
import com.alumni.service.DonationService;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class DonationServiceImpl implements DonationService {
    @Autowired
    private DonationProjectMapper projectMapper;
    @Autowired
    private DonationMapper donationMapper;

    @Override
    public Result<PageResult<DonationProject>> listProjects(Integer pageNum, Integer pageSize) {
        Page<DonationProject> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<DonationProject> qw = new LambdaQueryWrapper<>();
        qw.eq(DonationProject::getDeleted, 0).orderByDesc(DonationProject::getCreateTime);
        Page<DonationProject> result = projectMapper.selectPage(page, qw);
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize()));
    }

    @Override
    public Result<DonationProject> getProjectById(Long id) {
        return Result.success(projectMapper.selectById(id));
    }

    @Override
    public Result<?> saveProject(DonationProjectDTO dto) {
        DonationProject p = new DonationProject();
        BeanUtils.copyProperties(dto, p);
        p.setCurrentAmount(BigDecimal.ZERO);
        p.setDonorCount(0);
        projectMapper.insert(p);
        return Result.success();
    }

    @Override
    public Result<?> updateProject(DonationProjectDTO dto) {
        DonationProject p = new DonationProject();
        BeanUtils.copyProperties(dto, p);
        projectMapper.updateById(p);
        return Result.success();
    }

    @Override
    public Result<?> deleteProject(Long id) {
        projectMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> donate(DonationDTO dto, Long alumniId) {
        String orderNo = "D" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Donation d = new Donation();
        d.setProjectId(dto.getProjectId());
        d.setAlumniId(alumniId);
        d.setAmount(dto.getAmount());
        d.setPayMethod(dto.getPayMethod());
        d.setOrderNo(orderNo);
        d.setStatus(0);
        d.setRemark(dto.getRemark());
        donationMapper.insert(d);
        return Result.success(orderNo);
    }

    @Override
    public Result<?> confirmPayment(String orderNo) {
        LambdaQueryWrapper<Donation> qw = new LambdaQueryWrapper<>();
        qw.eq(Donation::getOrderNo, orderNo);
        Donation d = donationMapper.selectOne(qw);
        if (d != null) {
            d.setStatus(1);
            d.setCertificateNo("CERT" + System.currentTimeMillis());
            donationMapper.updateById(d);
            projectMapper.updateAmount(d.getProjectId(), d.getAmount());
        }
        return Result.success();
    }

    @Override
    public Result<?> getMyDonations(Long alumniId) {
        return Result.success(donationMapper.selectByAlumniId(alumniId));
    }
}
