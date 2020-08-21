package com.coding;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-21 17:10
 */
public class Proxy  {


    public static <T> T newInstance(ClassLoader classLoader, Class<T> interfaceClass, Interceptor interceptor) {
        File javaBody = null;
        try {
            javaBody = buildClass(interfaceClass);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null, javaBody.getAbsolutePath());
//            System.out.println(result == 0 ? "编译成功" : "编译失败");
            if (result != 0) {
                throw new Exception("编译失败");
            }
            String className = interfaceClass.getPackage().getName() + "." + javaBody.getName().replace(".java", "");
            Class<?> aClass = classLoader.loadClass(className);
            Object o = aClass.getConstructor(Interceptor.class).newInstance(interceptor);
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (javaBody != null) {
                //删除.java .class 文件
//                try {
//                    System.out.println(javaBody.delete());
//                    new File(javaBody.getAbsolutePath().replace(".java", "") + ".class").delete();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    private static File buildClass(Class<?> interfaceClass) throws Exception {


        String packageName = interfaceClass.getPackage().getName();
        String simpleName = interfaceClass.getSimpleName();

        StringBuffer javaSource = new StringBuffer();
        javaSource.append("package " + interfaceClass.getPackage().getName() + ";\r\n");

        String proxyClassName = simpleName + "$Proxy";
        if (interfaceClass.isInterface()) {
            javaSource.append(String.format("\n\npublic  class %s implements %s.%s {\r\n\n\n", proxyClassName, packageName, simpleName));
        }else{
            javaSource.append(String.format("\n\npublic  class %s extends %s.%s {\r\n\n\n", proxyClassName, packageName, simpleName));
        }


        Method[] declaredMethods = interfaceClass.getDeclaredMethods();


        //添加 Method对象

        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            if (Modifier.isPrivate(declaredMethod.getModifiers())) {
                continue;
            }
            javaSource.append("private static java.lang.reflect.Method m" + i + ";\n\n");
        }

        javaSource.append("static {");
        javaSource.append("    try {");

        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            if (Modifier.isPrivate(declaredMethod.getModifiers())) {
                continue;
            }
            String methodName = declaredMethod.getName();
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            String methodParameterStr = null;
            if (parameterTypes.length > 0) {

                for (Class<?> parameterType : parameterTypes) {
                    methodParameterStr = (methodParameterStr == null ? parameterType.getName() : methodParameterStr + "," + parameterType.getName()) + ".class";
                }
            }
            String parm = methodParameterStr == null ? "" : "," + methodParameterStr;
            javaSource.append("m" + i + " = Class.forName(\"" + interfaceClass.getName() + "\").getDeclaredMethod(\"" + methodName + "\"" + parm + ");\n");
        }
        javaSource.append("    }catch (Exception e){");
        javaSource.append("         e.printStackTrace();");
        javaSource.append("    }");
        javaSource.append("}");


        //添加 函数处理器
        javaSource.append("\n\nprivate com.coding.Interceptor h; \r\n");
        //添加构造函数
        javaSource.append("public " + proxyClassName + "(com.coding.Interceptor h){ this.h = h;} \r\n");


        //实现接口方法
        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            if (Modifier.isPrivate(declaredMethod.getModifiers())) {
                continue;
            }
            String methodName = declaredMethod.getName();
            Class<?> returnType = declaredMethod.getReturnType();
            String returnTypeName = returnType.getName();
            System.out.println(methodName + "= " + returnType.isPrimitive());
            System.out.println(methodName + "= " + returnType);
            System.out.println(methodName + "= " + returnTypeName);
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            String methodParameterStr = null;
            if (parameterTypes.length > 0) {
                for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                    Class<?> parameterType = parameterTypes[0];
                    methodParameterStr = methodParameterStr == null ? parameterType.getName() + " args" + i1 : methodParameterStr + ", " + parameterType.getName() + " args" + i1;
                }
            }

            methodParameterStr = methodParameterStr == null ? "" : methodParameterStr;
            javaSource.append("public " + returnTypeName + " " + methodName + "(" + methodParameterStr + "){\n");
            if (!returnTypeName.equals("void")) {
                String p = null;
                if (parameterTypes.length > 0) {
                    String p2 = "";
                    for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                        p2 = p2.isEmpty() ? "args" + i1 : p2 + ",args" + i1;
                    }
                    p = "new Object[]{" + p2 + "}";
                } else {
                    p = "new Object[0]";
                }
                javaSource.append(" return  (" + returnTypeName + ") this.h.invoke(this, m" + i + ", " + p + ");");
            } else {

                String p = null;
                if (parameterTypes.length > 0) {
                    String p2 = "";
                    for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                        p2 = p2.isEmpty() ? "args" + i1 : p2 + ",args" + i1;
                    }
                    p = "new Object[]{" + p2 + "}";
                } else {
                    p = "new Object[0]";
                }


                javaSource.append("   this.h.invoke(this, m" + i + ", " + p + ");");
            }
            javaSource.append("\n}\n");

        }

        javaSource.append("\n}\r\n");


        File file = new File(".", proxyClassName + ".java");
        FileOutputStream out = new FileOutputStream(file);
        out.write(javaSource.toString().getBytes());
        out.flush();
        out.close();
        return file;
    }

}
