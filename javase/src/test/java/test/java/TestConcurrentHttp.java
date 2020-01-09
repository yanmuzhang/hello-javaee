package test.java;

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: sufeng
 * @create: 2020-01-08 16:32
 */
public class TestConcurrentHttp {

    int count = 10;

    static String[] v = new String[]{
            "http://localhost:8080/api/v1/ping",
            "GET",
            null};


    @Test
    public void test1() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                System.out.println("。。。。。。。。。。。已全部准备就绪。。。。。。。。。。");
            }
        });
        for (int i = 0; i < count; i++) {
            new Thread(new ReqHttp(cyclicBarrier, (i + 1), v[0], v[1], v[2]), "Thread-" + (i + 1)).start();
        }

        new Scanner(System.in).next();
    }
}


class ReqHttp implements Runnable {
    CyclicBarrier cyclicBarrier;
    int num;

    String url;
    String method;
    String params;

    public ReqHttp(CyclicBarrier cyclicBarrier, int num, String url, String method, String params) {
        this.cyclicBarrier = cyclicBarrier;
        this.num = num;
        this.url = url;
        this.method = method;
        this.params = params;
    }

    @Override
    public void run() {
        System.out.println("任务 【" + num + "】 已经准备就绪");
        try {
            cyclicBarrier.await();
            System.out.println("任务 【" + num + "】 开始执行"+System.currentTimeMillis());
            String result = doHttp(url + "?name=" + Thread.currentThread().getName(), method, params);
            System.out.println("任务 【" + num + "】 完毕执行 结果【" + result + "】"+System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String doHttp(String url, String method, String params) {
        String result = null;
        String line = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            urlConnection.setRequestMethod(method);
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setDoInput(true);
            if (params != null && params.length() > 0) {
                urlConnection.setDoOutput(true);
                outputStream = urlConnection.getOutputStream();
                outputStream.write(params.getBytes());
            }

            int responseCode = urlConnection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode >= 200 && responseCode < 300) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) sb.append(line);
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
