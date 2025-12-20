package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ComplaintEntity;
import com.utils.PageUtils;

import java.util.Map;

public interface ComplaintService extends IService<ComplaintEntity> {
    PageUtils queryPage(Map<String, Object> params, Wrapper<ComplaintEntity> wrapper);
}
