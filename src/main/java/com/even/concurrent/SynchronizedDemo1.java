package com.even.concurrent;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/18 14:46
 */
public class SynchronizedDemo1 {
    static ID id = new ID();

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println(name + ":" + ID.getID());
            }
        };
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println(name + ":" + id.getID1());
            }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r1);
        t1.start();
        Thread.sleep(10000);
        t2.start();
    }

    private static class ID {
        private static int counter = 0;

        public static synchronized int getID() {
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return counter++;
        }

        private static int num = 1;

        public int getID1() {

            return num++;
        }
    }
}
