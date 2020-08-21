package com.jdk;

import com.GithubClient;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @description: todo
 * @create: 2020-08-20 14:11
 */
public class TestJdkProxy {


    @Test
    public void test1(){


        GithubClient githubClient = (GithubClient)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{GithubClient.class}, new HttpInvocationHandler());

        String auth = githubClient.auth("admin", "password");

        System.out.println(auth);
    }
}
