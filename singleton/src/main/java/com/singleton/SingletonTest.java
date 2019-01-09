package com.singleton;

/**
 * @author: zhangchao
 * @time: 2019-01-04 11:20
 **/
public class SingletonTest {

    public String hello(){
        return getClass().getName();
    }


    public static void main(String[] args) {
        SingletonTest singletonTest = Singleton.INSTENCE.singletonTest;
        System.out.println(singletonTest.hello());

        System.out.println(1<<4);
    }



}

enum Singleton{
    INSTENCE;
    SingletonTest singletonTest;

    Singleton() {
        singletonTest = new SingletonTest();
    }
}


