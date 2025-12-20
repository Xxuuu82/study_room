package com.service.impl;

import com.dao.ShenSuDao;
import com.entity.ShenSu;
import com.service.ShenSuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ShenSuServiceImpl implements ShenSuService {
    @Resource
    private ShenSuDao shenSuDao;

    @Override
    public List<ShenSu> getPageList(Map<String, Object> params) {
        int page = (Integer) params.get("page");
        int size = (Integer) params.get("size");
        params.put("start", (page - 1) * size);
        return shenSuDao.selectPage(params);
    }

    @Override
    public Long getTotalCount(Map<String, Object> params) {
        return shenSuDao.selectCount(params);
    }

    @Override
    public int handleShenSu(Long id, Integer status, String chuliRen, String chuliBeizhu) {
        return shenSuDao.updateChuliStatus(id, status, chuliRen, chuliBeizhu);
    }

    @Override
    public ShenSu getShenSuById(Long id) {
        return shenSuDao.selectById(id);
    }
}
