package com.webapi;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.user.interfaces.UserInterface;

/**
 * @author: zhangchao
 * @time: 2018-12-28 16:19
 **/
public class Application {

    public static void main(String[] args) {
        ReferenceConfig<UserInterface> referenceConfig = new ReferenceConfig<UserInterface>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("multicast://224.0.0.1:1234"));
        referenceConfig.setInterface(UserInterface.class);
        UserInterface greetingService = referenceConfig.get();
        System.out.println(greetingService.getUserInfo("world"));
    }

}
