package com;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 15:12
 */
public class HttpRequest implements Request {

    String url;
    String method;
    Map<String, String> header = new HashMap<String, String>();
    Map<String, Object> params = new HashMap<String, Object>();

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return header;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", header=" + header +
                ", params=" + params +
                '}';
    }

    public static final class HttpRequestBuilder {
        public HttpRequestBuilder(Method me, Object[] args) {
            String url = "";
            String httpMethod = "";
            Map<String, String> header = new HashMap<String, String>();
            Map<String, Object> params = new HashMap<String, Object>();


            HTTP.Path httpPath = me.getAnnotation(HTTP.Path.class);
            if (httpPath != null) {
                withUrl(httpPath.value());
            }

            HTTP.GET httpGet = me.getAnnotation(HTTP.GET.class);
            if (httpGet != null) {
                withMethod("GET");
            }
            HTTP.POST httpPost = me.getAnnotation(HTTP.POST.class);
            if (httpPost != null) {
                withMethod("POST");
            }
            HTTP.Headers httpHeaders = me.getAnnotation(HTTP.Headers.class);
            if (httpHeaders != null) {
                for (String h : httpHeaders.value()) {
                    String[] split = h.split(":");
                    header.put(split[0], split[1]);
                }
            }

            Parameter[] parameters = me.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                HTTP.Parameter httpParameter = parameter.getAnnotation(HTTP.Parameter.class);
                if (httpParameter != null && httpParameter.value() != null) {
                    params.put(httpParameter.value(), args[i]);
                } else {
                    params.put(parameter.getName(), args[i]);
                }
            }

            withParams(params);
            withHeader(header);
        }

        String url;
        String method;
        Map<String, String> header = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<String, Object>();

        private HttpRequestBuilder() {
        }

        public static HttpRequestBuilder aHttpRequest(Method me, Object[] args) {
            return new HttpRequestBuilder(me, args);
        }

         HttpRequestBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

         HttpRequestBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

         HttpRequestBuilder withHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

         HttpRequestBuilder withParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public HttpRequest build() {
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.params = this.params;
            httpRequest.header = this.header;
            httpRequest.method = this.method;
            httpRequest.url = this.url;
            return httpRequest;
        }
    }
}
