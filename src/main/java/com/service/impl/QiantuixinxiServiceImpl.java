package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.QiantuixinxiDao;
import com.entity.QiantuixinxiEntity;
import com.entity.view.QiantuixinxiView;
import com.service.QiantuixinxiService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("qiantuixinxiService")
public class QiantuixinxiServiceImpl extends ServiceImpl<QiantuixinxiDao, QiantuixinxiEntity> implements QiantuixinxiService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<QiantuixinxiEntity> page = this.selectPage(
                new Query<QiantuixinxiEntity>(params).getPage(),
                new EntityWrapper<QiantuixinxiEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<QiantuixinxiEntity> wrapper) {
        Page<QiantuixinxiView> page = new Query<QiantuixinxiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<QiantuixinxiView> selectListView(Wrapper<QiantuixinxiEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public QiantuixinxiView selectView(Wrapper<QiantuixinxiEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }

    // ========== 新增：根据预约单号查询签退记录 ==========
    @Override
    public QiantuixinxiEntity getByYuyueDanHao(String yuyuedanhao) {
        return this.selectOne(new EntityWrapper<QiantuixinxiEntity>()
                .eq("yuyuedanhao", yuyuedanhao));
    }
}