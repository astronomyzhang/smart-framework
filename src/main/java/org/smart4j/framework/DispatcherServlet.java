package org.smart4j.framework;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.utils.JsonUtil;
import org.smart4j.framework.utils.ReflectionUtil;
import org.smart4j.framework.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet(urlPatterns = "/", loadOnStartup = 0)
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

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            //获取请求方法与请求路径
            String requestMethod = req.getMethod();
            String requestPath = req.getPathInfo();

            if(requestPath.equals("/favicon.ico")){
                return;
            }

            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if(handler != null){
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数
                Param param;
                if(UploadHelper.isMultipart(req)){
                    param = UploadHelper.createParam(req);
                }else{
                    param = RequestHelper.createParam(req);
                }

                Object result;
                //调用Action方法
                Method actionMethod = handler.getActionMethod();
                if(param.isEmpty()){
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                }else{
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理Action方法返回值
                if(result instanceof View){
                    handleViewResult((View) result, req, resp);
                }else if(result instanceof Data){
                    handleDataResult((Data) result, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }

    }

    /**
     *处理 View 结果
     *@author Garwen
     *@date 2019-12-17 15:39
     *@param view
     *@param req
     *@param resp
     *@return void
     *@throws
     */
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
    }

    /**
     *处理 Data 结果
     *@author Garwen
     *@date 2019-12-17 15:40
     *@param data
     *@param resp
     *@return void
     *@throws
     */
    private void handleDataResult(Data data, HttpServletResponse resp) throws IOException {
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
