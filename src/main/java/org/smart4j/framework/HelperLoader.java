package org.smart4j.framework;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.utils.ClassUtil;

/**
 *加载静态模块，初始化 Helper 类
 *@author Garwen
 *@date 2019/12/4 0:02
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList={BeanHelper.class, ClassHelper.class, ConfigHelper.class, ControllerHelper.class,
            IocHelper.class};
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
