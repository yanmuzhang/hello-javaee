package network.call.chain.bean;

import java.io.Serializable;

/**
 * @author: sufeng
 * @create: 2020-01-15 15:10
 */
public class ChainData implements Serializable {

    public static class Chain {
        String host;
        String projectName;

        public Chain(String host, String projectName) {
            this.host = host;
            this.projectName = projectName;
        }

        public String getHost() {
            return host;
        }

        public String getProjectName() {
            return projectName;
        }
    }

    private static final long serialVersionUID = 1533975706441390L;

    private String id;//链路ID - 一个请求链路ID应该一样
    private String chainsList;//链路系统 多个使用,分割
    private String url;
    private String body;
    private String headers;
    private Integer responseStatus;
    private String responseBody;


    public void addChains(Chain chain) {
        String t = chain.getHost() + "|" + chain.getProjectName();
        if (this.chainsList == null || this.chainsList.length() == 0) {
            this.chainsList = t;
        } else {
            this.chainsList += "," + t;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChainsList() {
        return chainsList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
