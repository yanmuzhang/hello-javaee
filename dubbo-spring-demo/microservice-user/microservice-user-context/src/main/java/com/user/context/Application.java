package com.user.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: zhangchao
 * @time: 2018-12-29 16:53
 **/
public class Application {
    /**
     * To get ipv6 address to work, add
     * System.setProperty("java.net.preferIPv6Addresses", "true");
     * before running your application.
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo.xml"});
        context.start();
        System.in.read(); // press any key to exit
    }
}
