package test.java;

import org.junit.Test;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏
 *
 * @author: sufeng
 * @create: 2020-01-07 17:19
 */
public class TestCyclicBarrier {


    @Test
    public void test1() {
        System.out.println("start");

        Integer threadCount = 10;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("准备就绪");
            }
        });

        for (Integer i = 0; i < threadCount; i++) {
            new Thread(new MyThread(cyclicBarrier, i * 1000), "Thread " + i).start();
        }
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
}

class MyThread implements Runnable {
    CyclicBarrier cyclicBarrier;
    Integer sheep;

    public MyThread(CyclicBarrier cyclicBarrier, int sheep) {
        this.cyclicBarrier = cyclicBarrier;
        this.sheep = sheep;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(sheep);
            System.out.println("Thread :" + Thread.currentThread().getName() + "开始");
            cyclicBarrier.await();
            System.out.println("Thread :" + Thread.currentThread().getName() + "通过了"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}