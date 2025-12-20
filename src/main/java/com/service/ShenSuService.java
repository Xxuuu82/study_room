package com.service;

import com.entity.ShenSu;

import java.util.List;
import java.util.Map;

public interface ShenSuService {
    List<ShenSu> getPageList(Map<String, Object> params);
    Long getTotalCount(Map<String, Object> params);
    int handleShenSu(Long id, Integer status, String chuliRen, String chuliBeizhu);
    ShenSu getShenSuById(Long id);
}
