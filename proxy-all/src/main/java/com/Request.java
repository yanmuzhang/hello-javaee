package com;

import java.util.Map;

/**
 * @author: zhangc/jaguar_zc@sina.com
 * @create: 2020-08-20 15:11
 */
public interface Request {

    String getUrl();

    String getMethod();

    Map<String, String> getHeaders();

    Map<String, Object> getParams();
}
