package io.sufeng.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author: sufeng
 * @create: 2020-01-19 09:43
 */
public class GsonJson implements JSON {


    private Gson newGson(){
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        return gson;
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
