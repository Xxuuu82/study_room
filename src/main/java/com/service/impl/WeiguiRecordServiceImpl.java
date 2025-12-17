package com.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.WeiguiRecordDao;
import com.entity.WeiguiRecord;
import com.service.WeiguiRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 违规记录Service实现类（适配MyBatis-Plus 2.3版本）
 */
@Service
public class WeiguiRecordServiceImpl extends ServiceImpl<WeiguiRecordDao, WeiguiRecord> implements WeiguiRecordService {

    @Override
    public List<WeiguiRecord> selectList(Wrapper<WeiguiRecord> wrapper) {
        // 调用BaseMapper的selectList方法（低版本核心适配点）
        return baseMapper.selectList(wrapper);
    }

    // 实现原生SQL查询方法
    @Override
    public List<WeiguiRecord> selectListBySql(String sql) {
        return baseMapper.selectListBySql(sql);
    }

    @Override
    public Integer selectCountBySql(String sql) {
        return baseMapper.selectCountBySql(sql);
    }
}