package com.cglib;

import com.GithubClient;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @description: todo
 * @create: 2020-08-20 15:23
 */
public class TestCglibProxy {


    @Test
    public void test1() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GithubClient.class);
        enhancer.setCallback(new HttpMethodInterceptor());
        GithubClient githubClient = (GithubClient) enhancer.create();

        System.out.println(githubClient.auth("admin", "1234567"));
    }
}
