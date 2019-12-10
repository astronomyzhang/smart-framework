package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DataBaseHelper;

import java.lang.reflect.Method;

public class TransactionProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(Proxychain proxychain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxychain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try {
                DataBaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = proxychain.doProxyChain();
                DataBaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Throwable throwable) {
                DataBaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
                throw throwable;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else{
            result = proxychain.doProxyChain();
        }
        return result;
    }
}
