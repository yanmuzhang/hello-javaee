package io.sufeng.http;

import io.sufeng.utils.HttpEntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Collections;
import java.util.Map;

/**
 * @author: sufeng
 * @create: 2020-01-10 14:18
 */
public interface HttpClient {

    HttpClient INSTANCE = new HttpClient() { };



    default String get(String url,ContentType contentType, Map<String, String> queryParam) {
        Map<String, String> headers = Collections.singletonMap("Content-Type",ContentType.APPLICATION_JSON.toString());
        return defaultRequest(url, "GET", headers, HttpEntityUtils.toEntity(contentType,queryParam));
    }

    default String post(String url, ContentType contentType, Map<String, String> body) {
        Map<String, String> headers = Collections.singletonMap("Content-Type",contentType.toString());
        return defaultRequest(url, "post", headers, HttpEntityUtils.toEntity(contentType,body));
    }

    default String delete(String url,ContentType contentType, Map<String, String> body) {
        Map<String, String> headers = Collections.singletonMap("Content-Type",contentType.toString());
        return defaultRequest(url, "delete", headers, HttpEntityUtils.toEntity(contentType,body));
    }

    default String put(String url,ContentType contentType, Map<String, String> body) {
        Map<String, String> headers = Collections.singletonMap("Content-Type",contentType.toString());
        return defaultRequest(url, "put", headers, HttpEntityUtils.toEntity(contentType,body));
    }

    default String request(String url,String method,ContentType contentType, Map<String, String> body) {
        Map<String, String> headers = Collections.singletonMap("Content-Type",contentType.toString());
        return defaultRequest(url, method, headers, HttpEntityUtils.toEntity(contentType,body));
    }



    static  String defaultRequest(String url, String method, Map<String, String> headers, String content) {
        String result = null;
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            String httpMethod = method.toUpperCase();
            HttpUriRequest httpUriRequest = null;

            if (httpMethod.equals("GET")) {
                httpUriRequest = new HttpGet(url + "?" + content);
            } else if (httpMethod.equals("POST")) {
                httpUriRequest = new HttpPost(url);
                ((HttpPost) httpUriRequest).setEntity(new StringEntity(content));
            } else if (httpMethod.equals("PUT")) {
                httpUriRequest = new HttpPut(url);
                ((HttpPut) httpUriRequest).setEntity(new StringEntity(content));
            } else if (httpMethod.equals("PATCH")) {
                httpUriRequest = new HttpPatch(url);
                ((HttpPatch) httpUriRequest).setEntity(new StringEntity(content));
            } else if (httpMethod.equals("DELETE")) {
                httpUriRequest = new HttpDelete(url + "?" + content);
            } else if (httpMethod.equals("HEAD")) {
                httpUriRequest = new HttpHead(url + "?" + content);
            } else if (httpMethod.equals("OPTIONS")) {
                httpUriRequest = new HttpOptions(url + "?" + content);
            } else if (httpMethod.equals("TRACE")) {
                httpUriRequest = new HttpPatch(url + "?" + content);
            }

            if (httpUriRequest == null) {
                throw new RuntimeException("不支持的 http请求方式");
            }

            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpUriRequest.setHeader(header.getKey(), header.getValue());
            }
            HttpResponse response = httpclient.execute(httpUriRequest);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            return result;
        }
    }

}
