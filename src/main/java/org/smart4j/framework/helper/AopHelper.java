package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
    static{
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for(Map.Entry<Class<?>, List<Proxy>> target : targetMap.entrySet()){
                Class<?> targetClass = target.getKey();
                List<Proxy> proxyList = target.getValue();

                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                //将原有Bean通过设置修改为代理Bean
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("AOP Failure ", e);
        }

    }

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

    /**
     * 获取目标类与代理对象之间的映射关系
     *@author Garwen
     *@date 2019/12/9 20:32
     *@params [proxyMap]
     *@return java.util.Map<java.lang.Class<?>,java.util.List<org.smart4j.framework.proxy.Proxy>>
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass:targetClassSet){
                Proxy proxy = (Proxy) targetClass.getDeclaredConstructor().newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
