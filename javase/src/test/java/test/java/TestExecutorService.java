package test.java;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author: sufeng
 * @create: 2020-01-07 14:48
 */
public class TestExecutorService {




    @Test
    public void test1(){

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new MyRun1(i));
        }
        executor.shutdown();

    }


    @Test
    public void test2(){
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            Future submit = executor.submit(new MyRun2(i));
            try {
                System.out.println(submit.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

}


class MyRun2 implements Callable {
    private Integer index;

    public MyRun2(Integer index) {
        this.index = index;
    }
    @Override
    public Object call() throws Exception {
        String format = String.format("Thread: %s ,  Index:%s", Thread.currentThread().getName(), index + "");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK-"+format;
    }
}

class MyRun1 implements Runnable{
    private Integer index;

    public MyRun1(Integer index) {
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println(String.format("Thread: %s ,  Index:%s",Thread.currentThread().getName(),index+""));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Thread: %s ,  Index:%s",Thread.currentThread().getName(),index+""));
    }
}