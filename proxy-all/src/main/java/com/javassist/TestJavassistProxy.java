package com.javassist;

import com.GithubClient;
import com.jdk.HttpInvocationHandler;
import javassist.*;

import java.lang.reflect.InvocationHandler;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 17:25
 */
public class TestJavassistProxy {

    public static void main(String[] args) throws Exception {

        Class<HttpInvocationHandler> httpInvocationHandler = HttpInvocationHandler.class;

        Class<GithubClient> githubClientClass = GithubClient.class;


        ClassPool pool = ClassPool.getDefault();
        CtClass proxyCc = pool.makeClass(githubClientClass.getName()+"$Proxy0");


        CtClass handlerCc = pool.get(InvocationHandler.class.getName());

        //创建一个成员变量  private InvocationHandler h;
        CtField handlerField = new CtField(handlerCc, "h", proxyCc);
        handlerField.setModifiers(Modifier.PRIVATE);
        //将字段添加到 NewProxyClass
        proxyCc.addField(handlerField);


        //创建一个构造方法   public NewProxyClass(handlerCc handlerCc){ this.h = handlerCc}
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{handlerCc}, proxyCc);
        ctConstructor.setBody("$0.h = $1;");

        //将构造方法加入 NewProxyClass
        proxyCc.addConstructor(ctConstructor);



        //添加接口
        CtClass interfaceCc = pool.get(githubClientClass.getName());
        proxyCc.addInterface(interfaceCc);


        //实现接口的方法
        CtMethod[] ctMethods = interfaceCc.getDeclaredMethods();
        for (int i = 0; i < ctMethods.length; i++) {
            // 核心逻辑在下方
            CtMethod ctMethod = ctMethods[i];
            System.out.println(ctMethod.getName());

//            methodBody += "System.out.println(\"" + i + "\");\n";
//            if (ctMethod.getReturnType() != CtPrimitiveType.voidType) {
//            }else{
//                methodBody += ";\n";
//            }
            //添加实现的方法



            /* 构造方法参数，如：new Class[] { String.class, Boolean.TYPE, Object.class } */
            String classParamsStr = "new Class[0]";
            if (ctMethod.getParameterTypes().length > 0) {
                for (CtClass parameterType : ctMethod.getParameterTypes()) {
                    classParamsStr = (classParamsStr.equals("new Class[0]") ? parameterType.getName() : classParamsStr + "," + parameterType.getName() )+ ".class";
                }
                classParamsStr = "new Class[]{" + classParamsStr + "}";
            }



            //创建方法对应的 Method 字段+
            CtField make = CtField.make(
                    String.format("private static java.lang.reflect.Method %s = Class.forName(\"%s\").getDeclaredMethod(\"%s\",%s);"
                    ,"m"+i,githubClientClass.getName(),ctMethod.getName(),classParamsStr),
                    proxyCc);
            proxyCc.addField(make);

            CtMethod newMethod = new CtMethod(ctMethod.getReturnType(), ctMethod.getName(), ctMethod.getParameterTypes(), proxyCc);

            String methodBody = "return ("+ctMethod.getReturnType().getName()+") $0.h.invoke($0, "+("m"+i)+", $args);";

            System.out.println("Invoke method: ");
            System.out.println(methodBody);
            System.out.println("Invoke method: ");
            newMethod.setBody(methodBody);
            proxyCc.addMethod(newMethod);
        }
        proxyCc.writeFile(".");


        GithubClient githubClient = (GithubClient)proxyCc.toClass().getConstructor(InvocationHandler.class).newInstance(new HttpInvocationHandler());


        System.out.println(githubClient.auth("admin", "123456"));


    }



}
