package org.smart4j.framework.helper;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.utils.PropsUtil;

import java.util.Properties;

/**
 * Config smart.properties
 *@author Garwen
 *@date 2019/12/1 18:18
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动
     *@author Garwen
     *@date 2019/12/1 18:25
     *@params []
     *@return java.lang.String
     */
    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     *获取JDBC的URL
     *@author Garwen
     *@date 2019/12/1 18:32
     *@params []
     *@return java.lang.String
     */
    public static String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取JDBC的用户名
     *@author Garwen
     *@date 2019/12/1 18:33
     *@params []
     *@return java.lang.String
     */
    public static String getJdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取JDBC的密码
     *@author Garwen
     *@date 2019/12/1 18:33
     *@params []
     *@return java.lang.String
     */
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     *@author Garwen
     *@date 2019/12/1 18:33
     *@params []
     *@return java.lang.String
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用JSP路径
     *@author Garwen
     *@date 2019/12/1 18:34
     *@params []
     *@return java.lang.String
     */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用静态资源路径
     *@author Garwen
     *@date 2019/12/1 18:36
     *@params []
     *@return java.lang.String
     */
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    public static int getAppUploadLimit(){
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT, 10);
    }
}
