package com.jdk;

import com.HttpRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 14:05
 */
public class HttpInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("在调用之前，我要干点啥呢？");
        System.out.println("Method:" + method);

        HttpRequest httpRequest = HttpRequest
                .HttpRequestBuilder
                .aHttpRequest(method,args)
                .build();
        System.out.println(httpRequest);
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
//        Object returnValue = method.invoke(this, args);
        String returnValue = "http.200";
        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("在调用之后，我要干点啥呢？");
        return returnValue;

    }
}
