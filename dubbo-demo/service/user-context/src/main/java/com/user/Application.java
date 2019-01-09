package com.user;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.user.context.UserServiceImpl;
import com.user.interfaces.UserInterface;

import java.io.IOException;

/**
 * @author: zhangchao
 * @time: 2018-12-28 16:13
 **/

public class Application {
    public static void main(String[] args) throws IOException {
        ServiceConfig<UserInterface> serviceConfig = new ServiceConfig<UserInterface>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        serviceConfig.setRegistry(new RegistryConfig("multicast://224.0.0.1:1234"));
        serviceConfig.setInterface(UserInterface.class);
        serviceConfig.setRef(new UserServiceImpl());
        serviceConfig.export();
        System.in.read();
    }
}
