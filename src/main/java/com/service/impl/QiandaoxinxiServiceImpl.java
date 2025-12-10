package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.QiandaoxinxiDao;
import com.entity.QiandaoxinxiEntity;
import com.entity.view.QiandaoxinxiView;
import com.service.QiandaoxinxiService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("qiandaoxinxiService")
public class QiandaoxinxiServiceImpl extends ServiceImpl<QiandaoxinxiDao, QiandaoxinxiEntity> implements QiandaoxinxiService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<QiandaoxinxiEntity> page = this.selectPage(
                new Query<QiandaoxinxiEntity>(params).getPage(),
                new EntityWrapper<QiandaoxinxiEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<QiandaoxinxiEntity> wrapper) {
        Page<QiandaoxinxiView> page = new Query<QiandaoxinxiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }


    @Override
    public List<QiandaoxinxiView> selectListView(Wrapper<QiandaoxinxiEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public QiandaoxinxiView selectView(Wrapper<QiandaoxinxiEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }


}
