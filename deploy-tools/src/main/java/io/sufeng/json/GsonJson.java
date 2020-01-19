package io.sufeng.json;

import com.google.gson.Gson;

/**
 * @author: sufeng
 * @create: 2020-01-19 09:43
 */
public class GsonJson implements JSON {


    private Gson newGson(){
        return new Gson();
    }

    @Override
    public String toString(Object object) {
        return newGson().toJson(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> tClass) {
        return (T)newGson().fromJson(json,tClass);
    }
}
