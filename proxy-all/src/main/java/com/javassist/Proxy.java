package com.javassist;

import javassist.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 16:16
 */
public abstract class Proxy {
    private static int proxy_index = 0;
    private static final String PROXY_FREFIX = "$Proxy";//生成的代理对象名称前缀

    public static <T> T newInstance(Class<T> interfaceClass, InvocationHandler invocationHandler) {
        try {
            ClassPool pool = ClassPool.getDefault();
            proxy_index++;

            //1. 创建一个代理class
            String proxyClassName = interfaceClass.getName() + "$Proxy" + proxy_index;
            CtClass proxyCc = pool.makeClass(proxyClassName);
            CtClass invocationHandlerH = pool.get(InvocationHandler.class.getName());


            //2. 创建一个成员变量  private InvocationHandler h;
            CtField handlerField = new CtField(invocationHandlerH, "h", proxyCc);
            handlerField.setModifiers(Modifier.PRIVATE);
            //3. 将成员变量添加到 代理类 $proxyClassName
            proxyCc.addField(handlerField);


            //4. 创建一个构造方法   public $proxyClassName(InvocationHandler invocationHandler){ this.h = invocationHandler}
            CtConstructor ctConstructor = new CtConstructor(new CtClass[]{invocationHandlerH}, proxyCc);
            ctConstructor.setBody("$0.h = $1;");
            //5. 将构造方法加入 $proxyClassName
            proxyCc.addConstructor(ctConstructor);

            //6. 实现接口的方法
            CtClass interfaceCc = pool.get(interfaceClass.getName());
            proxyCc.addInterface(interfaceCc);
            //7. 实现接口的方法
            for (int i = 0; i < interfaceCc.getDeclaredMethods().length; i++) {
                CtMethod interfaceMethod = interfaceCc.getDeclaredMethods()[i];
                String name = interfaceMethod.getName();
                int modifiers = interfaceMethod.getModifiers();
                String p = null;
                CtClass[] parameterTypes = interfaceMethod.getParameterTypes();
                if (parameterTypes.length > 0) {
                    for (CtClass parameterType : parameterTypes) {
                        p = p == null ? parameterType.getName() + ".class" : p + "," + parameterType.getName() + ".class";
                    }
                    p = "new Class[]{" + p + "}";
                } else {
                    p = "new Class[0]";
                }
                //7.1 创建一个该方法对应的Method成员变量
                String m = "private static java.lang.reflect.Method m" + i + " = Class.forName(\"" + interfaceClass.getName() + "\").getDeclaredMethod(\"" + interfaceMethod.getName() + "\"," + p + ");";
                System.out.println(m);
                CtField field = CtField.make(m, proxyCc);
                proxyCc.addField(field);

                //7.2 实现接口的方法
                String methodBody = "$0.h.invoke($0, m" + i + ",$args)";
                if (interfaceMethod.getReturnType() == CtPrimitiveType.voidType) {
                    CtMethod ctMethod = new CtMethod(CtPrimitiveType.voidType, name, parameterTypes, proxyCc);
                    ctMethod.setModifiers(modifiers);
                    ctMethod.setBody(methodBody + ";");
                    proxyCc.addMethod(ctMethod);
                } else {
                    CtMethod ctMethod = new CtMethod(interfaceMethod.getReturnType(), name, parameterTypes, proxyCc);
                    ctMethod.setModifiers(modifiers);
                    if (interfaceMethod.getReturnType() instanceof CtPrimitiveType) {
                        CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) interfaceMethod.getReturnType();
                        System.out.println("ctPrimitiveType:" + ctPrimitiveType.getGetMethodName());
                        methodBody = "return ((" + ctPrimitiveType.getWrapperName() + ")" + methodBody + ")." + ctPrimitiveType.getGetMethodName() + "()";
                    } else {
                        methodBody = "return (" + interfaceMethod.getReturnType().getName() + ") " + methodBody;
                    }
                    ctMethod.setBody(methodBody + ";");
                    proxyCc.addMethod(ctMethod);
                }
            }
//            proxyCc.writeFile(".");
            return (T) proxyCc.toClass().getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
