package org.smart4j.framework.helper;

import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.utils.ArrayUtil;
import org.smart4j.framework.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *Controller 助手类
 *@author Garwen
 *@date 2019-12-3 10:14
 */
public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
    //用于存放请求和处理类的映射关系
    static{
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();

        if(CollectionUtil.isNotEmpty(classSet)){
            for(Class<?> bean : classSet){
                Method[] controllerMethods = bean.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(controllerMethods)){
                    for(Method method:controllerMethods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array) && array.length==2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];

                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(bean, method);

                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     *获取Handler
     *@author Garwen
     *@date 2019-12-03 18:04
     *@param requestMethod
     *@param requestPath
     *@return org.smart4j.framework.bean.Handler
     *@throws
     */
    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }


}
