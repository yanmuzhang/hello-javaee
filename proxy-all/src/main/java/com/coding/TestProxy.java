package com.coding;

import com.AuthTokenService;
import com.GithubClient;
import com.HttpRequest;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-21 14:46
 */
public class TestProxy {


    Interceptor interceptor = new Interceptor() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            System.out.println("Interceptor => "+method.getName()+"   "+args.toString());
            return "";
        }
    };


    @Test
    public void test1() throws Exception {
        HttpRequest githubClient = Proxy.newInstance(getClass().getClassLoader(), HttpRequest.class, interceptor);
        System.out.println(githubClient.getUrl());
//        Class<?> aClass = getClass().getClassLoader().loadClass("com.GithubClient$Proxy");
//
//        System.out.println(aClass.getName());

    }

}
