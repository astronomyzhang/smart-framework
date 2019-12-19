package org.smart4j.framework.plugin.security.exception;

/**
 *认证异常（非法访问时抛出）
 *@author Garwen
 *@date 2019-12-19 20:58
 */

public class AuthcException extends Exception {
    public AuthcException() {
    }

    public AuthcException(String message) {
        super(message);
    }

    public AuthcException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthcException(Throwable cause) {
        super(cause);
    }
}
