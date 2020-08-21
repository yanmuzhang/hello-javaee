package com.coding;

import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-21 16:55
 */
public interface Interceptor {
    public Object invoke(Object proxy, Method method, Object[] args);
}
