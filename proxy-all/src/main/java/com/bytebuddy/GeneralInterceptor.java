package com.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 11:06
 */
public class GeneralInterceptor {

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments,
                            @Origin Method method) {
        // intercept any method of any signature

        return "void";
    }
}
