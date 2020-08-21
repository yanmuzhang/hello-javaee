package com.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @description: todo
 * @create: 2020-08-20 10:40
 */
public class LoggerInterceptor {

    public static String log(@SuperCall Callable<String> zuper)  throws Exception {
        System.out.println("Calling database");
        try {
            return zuper.call();
        } finally {
            System.out.println("Returned from database");
        }
    }
}
