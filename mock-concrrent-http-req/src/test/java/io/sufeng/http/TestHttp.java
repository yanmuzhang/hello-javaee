package io.sufeng.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author: sufeng
 * @create: 2020-01-10 14:14
 */
public class TestHttp {

    public static void main(String[] args)  throws Exception{
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpGet httpget = new HttpGet("http://localhost:8080/api/v1/user");

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            EntityUtils.consume(entity);
//
//            System.out.println("Initial set of cookies:");
//            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
//            if (cookies.isEmpty()) {
//                System.out.println("None");
//            } else {
//                for (int i = 0; i < cookies.size(); i++) {
//                    System.out.println("- " + cookies.get(i).toString());
//                }
//            }
//
//            HttpPost httpost = new HttpPost("https://portal.sun.com/amserver/UI/Login?" +
//                    "org=self_registered_users&" +
//                    "goto=/portal/dt&" +
//                    "gotoOnFail=/portal/dt?error=true");
//
//            List <NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("IDToken1", "username"));
//            nvps.add(new BasicNameValuePair("IDToken2", "password"));
//
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
//
//            response = httpclient.execute(httpost);
//            entity = response.getEntity();
//
//            System.out.println("Login form get: " + response.getStatusLine());
//            EntityUtils.consume(entity);
//
//            System.out.println("Post logon cookies:");
//            cookies = httpclient.getCookieStore().getCookies();
//            if (cookies.isEmpty()) {
//                System.out.println("None");
//            } else {
//                for (int i = 0; i < cookies.size(); i++) {
//                    System.out.println("- " + cookies.get(i).toString());
//                }
//            }

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }



    }
}
