package test.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sufeng
 * @create: 2020-01-08 10:30
 */
public class TestArrayList {
    static final int nThreads = Runtime.getRuntime().availableProcessors();

    @Test
    public void test1(){
        System.out.println(nThreads);

        ArrayList list = new ArrayList(2);
        list.add(1);
        list.add(1);
        System.out.println(list.size());
        list.add(2);
        System.out.println(list.size());
        list.add(2);
        System.out.println(list.size());
        list.add(2);
        System.out.println(list.size());

    }
}
