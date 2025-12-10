package com.entity.view;

import com.entity.XueshengEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 学生
 */
@TableName("xuesheng")
public class XueshengView extends XueshengEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public XueshengView() {
    }

    public XueshengView(XueshengEntity xueshengEntity) {
        try {
            BeanUtils.copyProperties(this, xueshengEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
