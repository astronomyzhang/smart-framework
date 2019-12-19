package org.smart4j.framework.plugin.security.exception;

/**
 *授权异常（权限无效时抛出）
 *@author Garwen
 *@date 2019-12-19 20:59
 */

public class AuthzException extends Exception {
    public AuthzException() {
    }

    public AuthzException(String message) {
        super(message);
    }

    public AuthzException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthzException(Throwable cause) {
        super(cause);
    }
}
