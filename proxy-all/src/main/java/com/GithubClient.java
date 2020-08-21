package com;

import java.nio.charset.CoderResult;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 09:41
 */
public interface GithubClient {

    @HTTP.Path("http://www.baidu.com")
    @HTTP.GET
    @HTTP.Headers("ContentType:application/json")
    String auth(@HTTP.Parameter("username") String username, @HTTP.Parameter("password") String password);

    @HTTP.Path("http://www.baidu.com")
    @HTTP.GET
    @HTTP.Headers("ContentType:application/json")
    int count();

    @HTTP.Path("http://www.baidu.com")
    @HTTP.GET
    @HTTP.Headers("ContentType:application/json")
    void ping();


    @HTTP.Path("http://www.baidu.com")
    @HTTP.GET
    @HTTP.Headers("ContentType:application/json")
    CoderResult result();
}