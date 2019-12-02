package org.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *@author Garwen
 *@date 2019/12/3 0:23
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     *@author Garwen
     *@date 2019/12/3 0:33
     *@params [cls]
     *@return java.lang.Object
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("New Instance Failure ", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     *@author Garwen
     *@date 2019/12/3 0:33
     *@params [obj, method, args]
     *@return java.lang.Object
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("Method invoke failure ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     *@author Garwen
     *@date 2019/12/3 0:36
     *@params [value, field, obj]
     *@return void
     */
    public static void setField(Object value, Field field, Object obj){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("Set Value error ", e);
            throw new RuntimeException(e);
        }
    }
}
