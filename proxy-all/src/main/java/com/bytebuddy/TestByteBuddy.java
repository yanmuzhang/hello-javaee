package com.bytebuddy;

import com.GithubClient;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 09:34
 */
public class TestByteBuddy {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
//        DynamicType.Unloaded<GithubClient> typeUnloaded = new ByteBuddy(ClassFileVersion.JAVA_V8)
//                .subclass(GithubClient.class)
//                .name("com.hello.bytebuddy.Type")
//                .method(ElementMatchers.named("print")).intercept(MethodDelegation.to(LoggerInterceptor.class))
////                .defineMethod("addMethod", String.class, Modifier.PUBLIC)
////                .withParameter(String.class, "mehtodName")
////                .intercept(MethodDelegation.to(Hi.class))
//                .make();
//        typeUnloaded.saveIn(new File(".","byte-buddy/class"));
//        Class<? extends GithubClient> loaded = typeUnloaded.load(TestByteBuddy.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
//                .getLoaded();
//        GithubClient abstractType = loaded .newInstance();
//        System.out.println(abstractType.print());
    }


    @Test
    public void test1() throws Exception {
        DynamicType.Unloaded<Object> typeUnloaded = new ByteBuddy(ClassFileVersion.JAVA_V8)
                .subclass(Object.class)
                .implement(GithubClient.class).intercept(FieldAccessor.ofBeanProperty())
                .name("GithubClientImpl")
                .method(ElementMatchers.any()).intercept(MethodDelegation.to(GeneralInterceptor.class))
//                .implement(GithubClient.class)
                .make();

        typeUnloaded.saveIn(new File(".","byte-buddy/class"));
        Class<?> loaded = typeUnloaded.load(TestByteBuddy.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
        Object o = loaded.newInstance();
    }



}
