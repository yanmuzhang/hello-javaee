import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author: sufeng
 * @create: 2020-01-15 15:00
 */
public class SimpleClass {

    public static void main( String[] args ) throws IOException {
        System.out.println("===========执行main方法=============");
        System.setProperty("hostName","baidu");
        fetch("http://8080.2.sufeng.io/boot/get/hello");
//
//        System.out.println("===========分割线=============");
//        System.setProperty("chainId","163");
//        fetch("http://www.163.com");
    }

    private static void fetch(final String address) throws IOException {

        final URL url = new URL(address);
        final URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        Map<String, List<String>> headerFields = connection.getHeaderFields();
        headerFields.forEach((k,v)->System.out.println(k+"  ==>  "+String.join(",",v)));

        try (final BufferedReader in = new BufferedReader(
                new InputStreamReader( connection.getInputStream())
        )){
            String inputLine = null;
            final StringBuffer sb = new StringBuffer();
            while ( (inputLine = in.readLine()) != null){
                sb.append(inputLine);
            }

            System.out.println("Content size:" + sb.length());
            System.out.println("Content :" + sb.toString());
        }

    }

}
