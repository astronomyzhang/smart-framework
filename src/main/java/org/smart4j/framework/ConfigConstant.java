package org.smart4j.framework;

/**
 * Constant Config Management
 *@author Garwen
 *@date 2019/12/1 18:05
 */
public interface ConfigConstant {
    String CONFIG_FILE = "smart4j.properties";
    String JDBC_DRIVER = "smart.framework.jdbc.driver";
    String JDBC_URL = "smart.framework.jdbc.url";
    String JDBC_USERNAME = "smart.framework.jdbc.username";
    String JDBC_PASSWORD = "smart.framework.jdbc.password";

    String APP_BASE_PACKAGE="smart.framework.app_base_package";
    String APP_JSP_PATH="smart.framework.jsp_path";
    String APP_ASSET_PATH="smart.framework.asset_path";

    String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit";
}
