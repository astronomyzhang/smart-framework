package org.smart4j.framework.plugin.security;

import java.util.Set;

/**
 *Smart Security插件接口
 * 可在应用中实现该接口，或者在smart.properties中提供以下基于SQL的配置项
 * <ul>
 *     <li>smart.plugin.SmartSecurity.jdbc.authc_query:根据用户名获取密码</li>
 *     <li>smart.plugin.SmartSecurity.jdbc.roles_query：根据用户名获取角色名集合</li>
 *     <li>smart.plugin.SmartSecurity.jdbc.permissions_query：根据用户名获取权限名集合</li>
 * </ul>
 *@author Garwen
 *@date 2019-12-19 20:34
 */

public interface SmartSecurity {
    /**
     *根据用户名获取密码
     *@author Garwen
     *@date 2019-12-19 20:40
     *@param username 用户名
     *@return 密码
     *@throws
     */
    String getPassword(String username);

    /**
     *根据用户名获取角色集合
     *@author Garwen
     *@date 2019-12-19 20:40
     *@param username 用户名
     *@return 角色名集合
     *@throws
     */
    Set<String> getRolesNameSet(String username);

    /**
     *根据角色名获取权限集合
     *@author Garwen
     *@date 2019-12-19 20:41
     *@param rolename 角色名
     *@return 权限名集合
     *@throws
     */
    Set<String> getPermissionNameSet(String rolename);
}
