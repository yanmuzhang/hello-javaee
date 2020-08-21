package com.cglib;

import com.HttpRequest;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 15:24
 */
public class HttpMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
        System.out.println("调用前");
        HttpRequest httpRequest = HttpRequest.HttpRequestBuilder.aHttpRequest(method, params).build();
        System.out.println(httpRequest);
//        Object result = proxy.invokeSuper(obj, params);
        Object result = "http.200";
        System.out.println(" 调用后" + result);
        return result;
    }
}
