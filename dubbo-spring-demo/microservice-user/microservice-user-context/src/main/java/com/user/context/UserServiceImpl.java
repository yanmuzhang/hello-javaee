package com.user.context;

import com.alibaba.dubbo.rpc.RpcContext;
import com.user.client.UserClient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhangchao
 * @time: 2018-12-29 16:33
 **/
public class UserServiceImpl implements UserClient {


    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}