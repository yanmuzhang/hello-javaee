package io.sufeng.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: sufeng
 * @create: 2020-01-10 15:11
 */
public class HttpEntityUtils {

    public static String toEntity(ContentType contentType,Map<String, String> queryParam){
        if(contentType ==ContentType.APPLICATION_JSON){
            return toJson(queryParam);
        }else{
            return toQueryParam(queryParam);
        }
    }


    public static String toQueryParam(Map<String, String> queryParam) {
        return StringUtils.join(Stream.of(queryParam).map(stringStringMap -> {
            List<String> list = new ArrayList<>();
            stringStringMap.forEach((k, v) -> {
                list.add(k + "=" + v);
            });
            return list;
        }).collect(Collectors.toList()), "&");
    }

    public static String toJson(Map<String, String> queryParam) {
        return new Gson().toJson(queryParam);
    }

}
