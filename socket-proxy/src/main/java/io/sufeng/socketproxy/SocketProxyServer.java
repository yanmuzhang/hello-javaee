package io.sufeng.socketproxy;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.logging.Logger;

/**
 * @author: sufeng
 * @create: 2020-04-02 10:00
 */
public class SocketProxyServer {

    Logger logger = Logger.getLogger(getClass().getName());

    public static String proxyHost;
    public static Integer proxyPort;
    public static String proxyCharset = "UTF-8";
    public static Integer DEFAULT_SERVER_PORT = 8000;

    ServerSocket serverSocket;


    public void start(Integer port) throws IOException {
        Integer serverPort = port == null ? DEFAULT_SERVER_PORT : port;
        logger.info(MessageFormat.format("port:{0}", serverPort + ""));
        logger.info(MessageFormat.format("ProxyHost:{0}", proxyHost + ""));
        logger.info(MessageFormat.format("ProxyPort:{0}", proxyPort + ""));

        SocketAddress socketAddress = new InetSocketAddress(serverPort);
        serverSocket = new ServerSocket();
        serverSocket.bind(socketAddress);
        logger.info(MessageFormat.format("bind :{0}", serverPort + ""));
        while (true) {
            try (Socket accept = serverSocket.accept();
                 InputStream serverInput = accept.getInputStream();
                 OutputStream serverOutput = accept.getOutputStream();
            ){

                int available = serverInput.available();
                byte[] outBytes = new byte[available];
                serverInput.read(outBytes);
                byte[] inBytes = sendDataToProxy(outBytes);
                serverOutput.write(inBytes);
                serverOutput.flush();
                logger.info("执行完毕 等待连接");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int byteToInt(byte[] b) {
        return (((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + ((b[3] & 0xFF) << 0));
    }


    public byte[] sendDataToProxy(byte[] outBytes) {
        try (Socket cSocket = new Socket()) {
            cSocket.setSoTimeout(30000);
            cSocket.connect(new InetSocketAddress(proxyHost, proxyPort));
            DataOutputStream dout = new DataOutputStream(cSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(cSocket.getInputStream());
            logger.info("send data => "+new String(outBytes, proxyCharset));
            dout.write(outBytes);
            dout.flush();
            cSocket.shutdownOutput();

            byte[] recv_bytes_first_4 = new byte[4];
            dis.read(recv_bytes_first_4, 0, 4);//��ȡ4���ֽ�

            String content_4_byte = new String(recv_bytes_first_4, "GBK"); //���Ƚ�ǰ4��byteת�����ַ���,�ж��Ƿ�δERTL
            if ("ERTL".equals(content_4_byte)) {
                logger.info("error:transaction limit!");
                return new byte[0];
            }


            int rec_len = byteToInt(recv_bytes_first_4); //��bytes����ת��Ϊint
            if (rec_len == 0) { //�������Ϊ0���ȴ���δ�յ�����
                logger.info("error:recv fail!");
                return new byte[0];
            }
            /*
             * ��ȡ����
             */
            byte[] tmp = new byte[rec_len];
            dis.readFully(tmp);

//            byte[] inBytes = new byte[dis.available()];
//            dis.read(inBytes);
            logger.info("read data => "+new String(tmp, proxyCharset));
            cSocket.shutdownInput();
            dout.close();
            dis.close();
            return tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    /**
     * java -classpath socket-proxy-1.0-SNAPSHOT.jar io.sufeng.socketproxy.SocketProxyServer 8000 192.168.56.212 17184 GBK
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length >= 3) {
            proxyHost = args[1];
            proxyPort = Integer.parseInt(args[2]);
            if (args.length >= 4) {
                proxyCharset = args[3];
            }

            new SocketProxyServer().start(Integer.parseInt(args[0]));
        }
    }
}
