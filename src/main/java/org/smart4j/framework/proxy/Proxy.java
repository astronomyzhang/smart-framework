package org.smart4j.framework.proxy;

/**
 *代理接口
 *@author Garwen
 *@date 2019-12-9 11:47
 */

public interface Proxy {
    /**
     *执行链式代理
     *@author Garwen
     *@date 2019-12-09 11:50
     *@param null
     *@return
     *@throws
     */
    Object doProxy(Proxychain proxychain) throws Throwable;
}
