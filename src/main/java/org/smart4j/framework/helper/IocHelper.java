package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.utils.ArrayUtil;
import org.smart4j.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入框架
 * BeanMap中为单例模式
 *
 * @author Garwen
 * @date 2019-12-3
 */
public final class IocHelper {
    static{
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //循环遍历Bean Map中的每个类
        for(Map.Entry<Class<?>, Object> beanItem: beanMap.entrySet()){
            Class<?> beanCls = beanItem.getKey();
            Object beanInstance = beanItem.getValue();
            //获取bean中的成员变量
            Field[] beanFields = beanCls.getDeclaredFields();
            if(ArrayUtil.isNotEmpty(beanFields)){
                for(Field beanField:beanFields){
                    if(beanField.isAnnotationPresent(Inject.class)){
                        Class<?> beanFieldClass = beanField.getType();
                        //在BeanMap中获取对应的实例
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if(beanFieldInstance!=null){
                            //通过反射初始化BeanField的值
                            ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }

        }
    }
}
