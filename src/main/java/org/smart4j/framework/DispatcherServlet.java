package org.smart4j.framework;

import org.smart4j.framework.bean.*;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.utils.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化Helper类
        HelperLoader.init();
        //获取 ServletContext 对象
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");

        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decode(StreamUtil.getString(req.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if(ArrayUtil.isNotEmpty(params)){
                    for(String param:params){
                        String[] array = StringUtil.splitString(param, "=");
                        if(ArrayUtil.isNotEmpty(array) && array.length==2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理Action方法返回值
            if(result instanceof View){
                View view = (View) result;
                String path = view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String, Object> entry:model.entrySet()){
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            }else if(result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();

                if(model!=null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }


    }
}
