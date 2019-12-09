package org.smart4j.framework.helper;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 *@author Garwen
 *@date 2019/12/2 22:07
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static{
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     *获取应用包名下所有类
     *@author Garwen
     *@date 2019/12/2 22:24
     *@params []
     *@return java.util.Set<java.lang.Class<?>>
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     *获取Service类
     *@author Garwen
     *@date 2019/12/2 22:23
     *@params []
     *@return java.util.Set<java.lang.Class<?>>
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取Controller类
     *@author Garwen
     *@date 2019/12/2 22:23
     *@params []
     *@return java.util.Set<java.lang.Class<?>>
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for(Class<?> cls: CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有bean类，包括Service、Controller等
     *@author Garwen
     *@date 2019/12/2 22:22
     *@params []
     *@return java.util.Set<java.lang.Class<?>>
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     *获取应用包名下某父类或接口的所有子类（或实现类）
     *@author Garwen
     *@date 2019-12-09 17:02
     *@param superClass
     *@return java.util.Set<java.lang.Class<?>>
     *@throws
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     *获取应用包名下带有某注解的所有类
     *@author Garwen
     *@date 2019-12-09 17:16
     *@param annotationClass
     *@return java.util.Set<java.lang.Class<?>>
     *@throws
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
