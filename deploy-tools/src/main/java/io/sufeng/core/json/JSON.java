package io.sufeng.core.json;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: sufeng
 * @create: 2020-01-19 09:40
 */
public interface JSON {

    JSON MP = new JSON() {
        JSON json ;
        {
            System.out.println("=====================================");
            System.out.println("ServiceLoader load JSON.class");
            ServiceLoader<JSON> load = ServiceLoader.load(JSON.class);
            Iterator<JSON> iterator = load.iterator();
            while(iterator.hasNext()) {
                json = iterator.next();
                System.out.println(json.getClass().getName());
            }
            System.out.println("=====================================");
        }


        @Override
        public String toString(Object object) {
            return json.toString(object);
        }

        @Override
        public <T> T toObject(String json, Class<T> tClass) {
            return this.json.toObject(json,tClass);
        }
    };

    String toString(Object object);
    <T> T toObject(String json,Class<T> tClass);
}
