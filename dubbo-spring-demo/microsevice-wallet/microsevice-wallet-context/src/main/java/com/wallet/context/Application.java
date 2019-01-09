package com.wallet.context;

import com.user.client.UserClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: zhangchao
 * @time: 2018-12-29 17:02
 **/
public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo.xml"});
        context.start();
        UserClient demoService = (UserClient) context.getBean("userClient"); // get remote service proxy
        while (true) {
            try {
                Thread.sleep(1000);
                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
