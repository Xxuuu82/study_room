package com.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ComplaintDao;
import com.entity.ComplaintEntity;
import com.service.ComplaintService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("complaintService")
public class ComplaintServiceImpl extends ServiceImpl<ComplaintDao, ComplaintEntity> implements ComplaintService {

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<ComplaintEntity> wrapper) {
        com.baomidou.mybatisplus.plugins.Page<ComplaintEntity> page =
                this.selectPage(new Query<ComplaintEntity>(params).getPage(), wrapper);
        return new PageUtils(page);
    }
}
