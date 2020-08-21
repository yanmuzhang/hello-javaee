package network.call.chain;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author: sufeng
 * @create: 2020-01-15 15:02
 */
public class SimpleClassTransformer implements ClassFileTransformer {


    public SimpleClassTransformer() {
        System.out.println("初始化 ：" + getClass().getName());
    }

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) {
        if (className.endsWith("sun/net/www/protocol/http/HttpURLConnection")) {
            System.out.println("transform => className:" + className);
            ClassPool classPool = ClassPool.getDefault();
            CtClass clazz = null;
            String chainId = System.getProperty("hostName");

            System.out.println("chainId:" + chainId);
            try {
                clazz = classPool.get("sun.net.www.protocol.http.HttpURLConnection");
                CtConstructor[] cs = clazz.getConstructors();
                for (CtConstructor constructor : cs) {
                    constructor.insertAfter("System.out.println(this.getURL());\n  ");
                    constructor.insertAfter("this.setRequestProperty(\"chainId3\", \"" + chainId + "\");\n  ");
                }
                CtMethod declaredMethod = clazz.getDeclaredMethod("getHeaderFields");
                String setHeader = "this.setRequestProperty(\"chainId3\", \"" + chainId + "\");\n  ";
                System.out.println(setHeader);
                declaredMethod.insertBefore(setHeader);
                byte[] byteCode = clazz.toBytecode();
                clazz.detach();

                return byteCode;
            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

