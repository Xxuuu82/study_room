package com.entity.view;

import com.entity.QuxiaoyuyueEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 取消预约
 */
@TableName("quxiaoyuyue")
public class QuxiaoyuyueView extends QuxiaoyuyueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public QuxiaoyuyueView() {
    }

    public QuxiaoyuyueView(QuxiaoyuyueEntity quxiaoyuyueEntity) {
        try {
            BeanUtils.copyProperties(this, quxiaoyuyueEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
