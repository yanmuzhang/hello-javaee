package network.call.chain;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author: sufeng
 * @create: 2020-01-15 17:16
 */
public abstract class BaseClassFileTransformer implements ClassFileTransformer {

    public abstract boolean isProxyClass(String className);

    public abstract String getProxyClassName();

    public abstract String insertConstructors();

    public abstract CtMethod insertMethod(CtClass clazz) throws CannotCompileException;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (isProxyClass(className)) {
            System.out.println(getClass().getName()+".transform()");
            ClassPool classPool = ClassPool.getDefault();
            CtClass clazz = null;
            try {
                clazz = classPool.get(getProxyClassName());
                CtConstructor[] cs = clazz.getConstructors();
                for (CtConstructor constructor : cs) {
                    constructor.insertAfter(insertConstructors());
                }

                clazz.addMethod(insertMethod(clazz));

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
