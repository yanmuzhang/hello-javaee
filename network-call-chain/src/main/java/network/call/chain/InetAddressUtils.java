package network.call.chain;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author: sufeng
 * @create: 2020-01-15 16:33
 */
public class InetAddressUtils {


    public static String getHostName() {
        String hostname = System.getProperty("hostname");
        try {
            hostname = hostname != null ? hostname : getInetAddress().getHostAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostname;
    }


    static InetAddress getInetAddress() throws SocketException {
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ipHost = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ipHost = (InetAddress) addresses.nextElement();
                if (ipHost != null && ipHost instanceof Inet4Address) {
                    return ipHost;
                }
            }
        }
        return ipHost;
    }
}
