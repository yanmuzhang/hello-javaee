

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Optional;
import java.util.Properties;

/**
 * @author: sufeng
 * @create: 2020-03-30 14:30
 */
public class Test {


    public static Optional<Socket> newProxySocket(String proxyIP, int proxyPort, String ip, int port) throws IOException {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));
            Socket socket = new Socket(proxy);
            socket.setSoTimeout(60000);
            socket.connect(new InetSocketAddress(ip, port));//服务器的ip及地址
            return Optional.ofNullable(socket);
        } catch (IOException e) {
            throw new IOException("socket连接失败", e);
        }
    }

    public static boolean ping(String ipAddress) throws Exception {
        int timeOut = 3000 ;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        return status;
    }

    public static void main(String[] args) throws Exception {

        Properties prop = System.getProperties();
        // 设置http访问要使用的代理服务器的地址
        prop.setProperty("http.proxyHost", "88.88.16.82");
        // 设置http访问要使用的代理服务器的端口
        prop.setProperty("http.proxyPort", "3128");
        // 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
        //prop.setProperty("http.nonProxyHosts", "localhost|192.168.0.*");
        // 设置安全访问使用的代理服务器地址与端口
        // 它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
        //prop.setProperty("https.proxyHost", "183.45.78.31");
        //prop.setProperty("https.proxyPort", "443");
        // 使用ftp代理服务器的主机、端口以及不需要使用ftp代理服务器的主机
        //prop.setProperty("ftp.proxyHost", "183.45.78.31");
        //prop.setProperty("ftp.proxyPort", "21");
        //prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.0.*");
        // socks代理服务器的地址与端口
        //prop.setProperty("socksProxyHost", "183.45.78.31");
        //prop.setProperty("socksProxyPort", "1080");
        //// 设置登陆到代理服务器的用户名和密码
//        Authenticator.setDefault(new MyAuthenticator("userName", "Password"));

//        Optional<Socket> socket = newProxySocket("88.88.16.82", 3218, "192.168.56.212", 22);
//        if (socket.isPresent()) {
//            Socket socket1 = socket.get();
//            System.out.println(socket1.isConnected());
//            socket1.getOutputStream().write(1);
//            System.out.println("==================");
//        }
        System.out.println(ping("192.168.56.212"));
        System.out.println(ping("192.168.56.212"));
        System.out.println(ping("192.168.56.212"));
        System.out.println(ping("192.168.56.212"));
        System.out.println(ping("192.168.56.212"));

//
//        URL url = new URL("http://www.baidu.com");
//        // 创建代理服务器
//        InetSocketAddress addr = new InetSocketAddress("88.88.16.82",3218);
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
////        URLConnection conn = url.openConnection(proxy);
//        URLConnection conn = url.openConnection();
//        InputStream in = conn.getInputStream();
//        String s = IOUtils.toString(in);
//        //System.out.println(s);
//        if(s.indexOf("百度")>0){
//            System.out.println("ok");
//        }
    }
}
