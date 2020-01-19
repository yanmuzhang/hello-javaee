package network.call.chain.header;

import java.net.HttpURLConnection;

/**
 *
 * 插入 添加Header的代码
 * @author: sufeng
 * @create: 2020-01-15 16:44
 */
public class HttpUrlConnectionHeaderInsert extends UrlConnectionHeaderInsert {


    @Override
    public String getProxyClassName() {
        return HttpURLConnection.class.getName();
    }
}
