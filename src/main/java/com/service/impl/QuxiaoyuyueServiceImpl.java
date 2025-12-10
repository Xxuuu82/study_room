package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.QuxiaoyuyueDao;
import com.entity.QuxiaoyuyueEntity;
import com.entity.view.QuxiaoyuyueView;
import com.service.QuxiaoyuyueService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("quxiaoyuyueService")
public class QuxiaoyuyueServiceImpl extends ServiceImpl<QuxiaoyuyueDao, QuxiaoyuyueEntity> implements QuxiaoyuyueService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<QuxiaoyuyueEntity> page = this.selectPage(
                new Query<QuxiaoyuyueEntity>(params).getPage(),
                new EntityWrapper<QuxiaoyuyueEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<QuxiaoyuyueEntity> wrapper) {
        Page<QuxiaoyuyueView> page = new Query<QuxiaoyuyueView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }


    @Override
    public List<QuxiaoyuyueView> selectListView(Wrapper<QuxiaoyuyueEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public QuxiaoyuyueView selectView(Wrapper<QuxiaoyuyueEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }


}
