package io.sufeng;

import org.apache.http.entity.ContentType;

import java.util.Map;

/**
 * @author: sufeng
 * @create: 2020-01-10 16:07
 */
public class HttpHistory {

    private String method;
    private String url;
    private ContentType contentType;
    private Map<String, String> headers;
    private Map<String, String> body;// 仅存放 application/x-www-form-urlencoded 表单数据
    private String rawBody;

    public HttpHistory() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }
}
