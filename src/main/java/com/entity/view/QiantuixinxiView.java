package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.QiantuixinxiEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


/**
 * 签到信息
 */
@TableName("qiantuixinxi")
public class QiantuixinxiView extends QiantuixinxiEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public QiantuixinxiView() {
    }

    public QiantuixinxiView(QiantuixinxiEntity qiantuixinxiEntity) {
        try {
            BeanUtils.copyProperties(this, qiantuixinxiEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
