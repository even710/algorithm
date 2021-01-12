package com.even.concurrent;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/18 15:11
 */
public class VolatileDemo {

    public static void main(String[] args) {


        StoppableThread thd = new StoppableThread();
        thd.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thd.stopThread();
    }

    static class StoppableThread extends Thread {

        boolean stopped;

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (!stopped)
                System.out.println(name + " is running");
        }

        void stopThread() {
            stopped = true;
        }
    }
}
