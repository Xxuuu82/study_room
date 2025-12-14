package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.HeimingdanDao;
import com.entity.HeiMingDan;
import com.service.HeiMingDanService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
// 泛型：Dao接口+实体类（与你的类名一致）
public class HeiMingDanServiceImpl extends ServiceImpl<HeimingdanDao, HeiMingDan> implements HeiMingDanService {

    /**
     * 分页查询黑名单列表（适配MyBatis-Plus 2.3）
     */
    @Override
    public Page<HeiMingDan> getBlackList(Page<HeiMingDan> page, Map<String, Object> params) {
        // MyBatis-Plus 2.3用EntityWrapper（无LambdaQueryWrapper）
        EntityWrapper<HeiMingDan> wrapper = new EntityWrapper<>();

        // 学号筛选（用like方法）
        if (params.get("xuehao") != null && !params.get("xuehao").toString().isEmpty()) {
            wrapper.like("xuehao", params.get("xuehao").toString());
        }
        // 状态筛选（用eq方法）
        if (params.get("shifoujiechu") != null && !params.get("shifoujiechu").toString().isEmpty()) {
            wrapper.eq("shifoujiechu", Integer.parseInt(params.get("shifoujiechu").toString()));
        }
        // 按加入时间倒序
        wrapper.orderBy("heimingdan_start_time", false);

        // MyBatis-Plus 2.3用selectPage方法（baseMapper是ServiceImpl自动注入的Dao）
        return this.selectPage(page, wrapper);
    }

    /**
     * 解除黑名单（适配MyBatis-Plus 2.3）
     */
    @Override
    public boolean jiechuBlackList(String xuehao) {
        EntityWrapper<HeiMingDan> wrapper = new EntityWrapper<>();
        wrapper.eq("xuehao", xuehao)
                .eq("shifoujiechu", 0); // 只解除未解除的记录

        HeiMingDan heimingdan = this.selectOne(wrapper);
        if (heimingdan == null) {
            return false;
        }
        // 更新状态和解除时间
        heimingdan.setShifoujiechu(1);
        heimingdan.setHeimingdanEndTime(new Date());

        // MyBatis-Plus 2.3用updateById方法
        return this.updateById(heimingdan);
    }
}