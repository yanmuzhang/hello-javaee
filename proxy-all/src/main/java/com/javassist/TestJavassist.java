package com.javassist;

import com.GithubClient;
import com.jdk.HttpInvocationHandler;
import javassist.*;
import org.junit.Test;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @description: todo
 * @create: 2020-08-20 15:33
 */
public class TestJavassist {
    private static final String PROXYFREFIX = "$Proxy";//生成的代理对象名称前缀
    private static final String PROXYSUFFIX = "Impl";//生成的代理对象名称前缀

    @Test
    public void test1() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass interfaceClient = pool.get("com.GithubClient");

        CtClass ctClass  = pool.makeClass("com.javassist.CGLIB_001");

        ctClass .setInterfaces(new CtClass[]{interfaceClient});

        //新增一个方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "joinFriend", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"i want to be your friend\");}");
        ctClass.addMethod(ctMethod);

        ctClass .writeFile(".");



        GithubClient githubClient = (GithubClient)ctClass.toClass().newInstance();

        System.out.println(githubClient.auth("admin", "1234567"));
    }

    @Test
    public void test2(){
//        Proxy<GithubClient> proxy = new Proxy<>(GithubClient.class);
//        GithubClient proxyOject = proxy.getProxyObject();
//        System.out.println("proxy Object name:"+proxyOject.getClass().getName());
//        System.out.println(proxyOject.auth("admin", "admin"));
    }


    @Test
    public void test3(){
        GithubClient githubClient = Proxy.newInstance(GithubClient.class, new HttpInvocationHandler());

        System.out.println(githubClient.count());
    }
}
