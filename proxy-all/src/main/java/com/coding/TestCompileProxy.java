package com.coding;

import com.GithubClient;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-21 14:46
 */
public class TestCompileProxy {


    @Test
    public void test1() throws Exception  {

        String aClass = createClass(GithubClient.class);

        File file = new File("D:\\tmp\\", "GithubClient$Proxy.java");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(aClass.getBytes());
        fileOutputStream.flush();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, file.getAbsolutePath());
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        //执行java 命令 , 空参数, 所在文件夹
        Process process = Runtime.getRuntime().exec("java GithubClient$Proxy", null, new File("D:\\tmp\\"));


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
    }

    public String createClass(Class<?> interfaceClass) {


        String packageName = interfaceClass.getPackage().getName();
        String simpleName = interfaceClass.getSimpleName();

        StringBuffer javaSource = new StringBuffer();
        javaSource.append("package " + interfaceClass.getPackage().getName() + ";\r\n");

        String proxyClassName = simpleName + "$Proxy";
        javaSource.append(String.format("\n\npublic  class %s implements %s.%s {\r\n\n\n", proxyClassName, packageName, simpleName));

        Method[] declaredMethods = interfaceClass.getDeclaredMethods();



        //添加 Method对象

        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            if (Modifier.isPrivate(declaredMethod.getModifiers())) {
                continue;
            }
            javaSource.append("private static java.lang.reflect.Method m" + i +";\n\n");
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
            javaSource.append("m" + i + " = Class.forName(\"" + interfaceClass.getName() + "\").getDeclaredMethod(\"" + methodName + "\"" + parm + ");\n\n");
        }
        javaSource.append("    }catch (Exception e){");
        javaSource.append("         e.printStackTrace();");
        javaSource.append("    }");
        javaSource.append("}");








        //添加 函数处理器
        javaSource.append("\n\nprivate com.compile.Interceptor h; \r\n");
        //添加构造函数
        javaSource.append("public " + proxyClassName + "(com.compile.Interceptor h){ this.h = h;} \r\n");


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
                        p2 = p2.isEmpty()? "args" + i1 :p2 + ",args" + i1;
                    }
                    p = "new Object[]{" + p2 + "}";
                } else {
                    p = "new Object[0]";
                }
                javaSource.append(" return  ("+returnTypeName+") this.h.invoke(this, m" + i + ", " + p + ");");
            } else {

                String p = null;
                if (parameterTypes.length > 0) {
                    String p2 = "";
                    for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                        p2 = p2.isEmpty()? "args" + i1 :p2 + ",args" + i1;
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


        return javaSource.toString();
    }


}
