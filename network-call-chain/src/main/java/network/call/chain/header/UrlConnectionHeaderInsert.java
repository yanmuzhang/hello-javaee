package network.call.chain.header;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import network.call.chain.BaseClassFileTransformer;
import network.call.chain.InetAddressUtils;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * 插入 添加Header的代码
 *
 * @author: sufeng
 * @create: 2020-01-15 16:44
 */
public class UrlConnectionHeaderInsert extends BaseClassFileTransformer {

    public UrlConnectionHeaderInsert() {
        System.out.println("初始化 ：" + getClass().getName());
    }

    @Override
    public boolean isProxyClass(String className) {
        return className.endsWith(getProxyClassName().replace(".", "/"));
    }

    @Override
    public String getProxyClassName() {
//        return "sun.net.www.protocol.http.HttpURLConnection";
        return HttpURLConnection.class.getName();
    }

    @Override
    public String insertConstructors() {
        String hostName = InetAddressUtils.getHostName();
        return "this.addCallChainHeader();\n" +
                "System.out.println(\"这是我插入到构造方法的代码!\");\n" +
                "this.setRequestProperty(\"hostname\", \"" + hostName + "\");";
    }

    @Override
    public CtMethod insertMethod(CtClass clazz) throws CannotCompileException {
        return CtNewMethod.make("" +
                "private void addCallChainHeader() { " +
                "    System.out.println(\"现在再执行private void addCallChainHeader() 方法\");" +

                "     this.setRequestProperty(\"chainId\", System.getProperty(\"chainId\")); " +
                "}", clazz);
    }

    public static void main(String[] args) {
    }

}
