package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 容器助手类
 *@author Garwen
 *@date 2019/12/3 0:38
 */
public final class BeanHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);
    private static final Map<Class<?>, Object> BEAN_MAP=new HashMap<Class<?>, Object>();

    /**
     * 定义Bean映射，定义Bean类和Bean实例的映射关系
     *@author Garwen
     *@date 2019/12/3 0:52
     *@params
     *@return
     */
    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> cls:beanClassSet){
            BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
        }
    }

    /**
     * 获取Bean映射
     *@author Garwen
     *@date 2019/12/3 0:52
     *@params []
     *@return java.util.Map<java.lang.Class<?>,java.lang.Object>
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     *@author Garwen
     *@date 2019/12/3 0:51
     *@params [cls]
     *@return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> cls){
        if(!BEAN_MAP.containsKey(cls)){
            LOGGER.error("can not get bean by class");
            throw new RuntimeException();
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     *设置BEAN实例
     *@author Garwen
     *@date 2019-12-09 16:56
     *@param cls
     *@param obj
     *@return void
     *@throws
     */
    public static void setBean(Class<?> cls, Object obj){
        BEAN_MAP.put(cls, obj);
    }
}
