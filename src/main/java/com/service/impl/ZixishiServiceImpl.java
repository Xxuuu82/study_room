package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ZixishiDao;
import com.entity.ZixishiEntity;
import com.entity.view.ZixishiView;
import com.service.ZixishiService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("zixishiService")
public class ZixishiServiceImpl extends ServiceImpl<ZixishiDao, ZixishiEntity> implements ZixishiService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ZixishiEntity> page = this.selectPage(
                new Query<ZixishiEntity>(params).getPage(),
                new EntityWrapper<ZixishiEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<ZixishiEntity> wrapper) {
        Page<ZixishiView> page = new Query<ZixishiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }


    @Override
    public List<ZixishiView> selectListView(Wrapper<ZixishiEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public ZixishiView selectView(Wrapper<ZixishiEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }


}
