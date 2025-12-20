package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.StudyDurationRankingDao;
import com.entity.StudyDurationRanking;
import com.service.StudyDurationRankingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 自习时长排名Service实现类
 * 修复点：
 * 1. 实现自定义selectByStudentId方法
 * 2. 实现deleteById方法（支持学号修改）
 * 3. 保留MP默认方法，不覆盖selectById
 */
@Service
public class StudyDurationRankingServiceImpl extends ServiceImpl<StudyDurationRankingDao, StudyDurationRanking> implements StudyDurationRankingService {

    // 自定义排名列表查询（调用Dao层自定义SQL）
    @Override
    public List<Map<String, Object>> getRankingList() {
        return baseMapper.selectRankingList();
    }

    // 核心：实现自定义详情查询（调用Dao层正确的SQL）
    @Override
    public StudyDurationRanking selectByStudentId(String studentId) {
        return baseMapper.selectByStudentId(studentId);
    }

    // 实现单条删除（支持学号修改的先删后加逻辑）
    @Override
    public boolean deleteById(String studentId) {
        return baseMapper.deleteById(studentId) > 0;
    }
}