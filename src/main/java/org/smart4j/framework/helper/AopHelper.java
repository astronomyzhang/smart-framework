package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AopHelper {
    /**
     *获取Aspect中设置的注解类
     *@author Garwen
     *@date 2019-12-09 17:57
     *@param aspect
     *@return java.util.Set<java.lang.Class<?>>
     *@throws
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotationClass = aspect.value();
        if(annotationClass!=null && annotationClass!=Aspect.class){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotationClass));
        }
        return targetClassSet;
    }

    /**
     *获取代理类与目标类集合之间的映射关系
     *@author Garwen
     *@date 2019-12-09 17:58
     *@param
     *@return java.util.Map<java.lang.Class<?>,java.util.Set<java.lang.Class<?>>>
     *@throws
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();

        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }

        return proxyMap;
    }
}
