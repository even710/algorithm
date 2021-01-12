package com.even.concurrent;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/18 11:06
 */
public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                int count = 0;
                while (!Thread.interrupted()) {
                    System.out.println(name + ":" + count++);
                }
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();
//        while (true) {
//            double n = Math.random();
//            if (n >= 0.499999 && n <= 0.500000) {
//                break;
//            }
//        }
        t1.interrupt();
        t2.interrupt();
        Thread.sleep(2);
        System.out.println(t1.isInterrupted());
        System.out.println(t1.isInterrupted());

    }
}
