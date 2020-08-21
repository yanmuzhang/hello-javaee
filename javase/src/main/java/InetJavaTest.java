

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @author: sufeng
 * @create: 2020-03-30 15:33
 */
public class InetJavaTest {
    private static String remoteInetAddr1 = "192.168.56.212";//需要连接的IP地址
    private static String remoteInetAddr2 = "88.88.16.82";//需要连接的IP地址
    /**
     * 传入需要连接的IP，返回是否连接成功
     * @param remoteInetAddr
     * @return
     */
    public static boolean isReachable(String remoteInetAddr) {
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(remoteInetAddr);
            reachable = address.isReachable(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reachable;
    }

    public static void main(String[] args) {
        URL url = null;
        Boolean bon = false;
        try {
            url = new URL("http://www.baidu.com");
            InetSocketAddress addr = new InetSocketAddress("88.88.16.82",3128);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);
            InputStream in = conn.getInputStream();//打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream
            System.out.println("连接正常");
            in.close();//关闭此输入流并释放与该流关联的所有系统资源。
        } catch (IOException e) {
            System.out.println("无法连接到：" + url.toString());
        }
        bon = isReachable(remoteInetAddr1);
        System.out.println("pingIP：" + bon);
        bon = isReachable(remoteInetAddr2);
        System.out.println("pingIP：" + bon);
    }
}