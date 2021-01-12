package com.even.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project Name: ai 重入锁ReentrantLock
 * Des: 默认主线程创建了一对工作线程，它们进入临界区，在其中模拟执行任务，然后离开临界区。
 * 它们使用ReentrantLock的lock()和unlock()方法来获取和释放一个重入锁。当一条线程调用lock()方法而锁又不可用时，
 * 这个线程就会一直禁用，并且不能被调度。直到锁变为可用。
 * Created by Even on 2019/4/23 13:52
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final ReentrantLock lock = new ReentrantLock();
        class Worker implements Runnable {
            private final String name;

            public Worker(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    if (lock.isHeldByCurrentThread())//在锁被当前线程持有时返回true
                        System.out.printf("Thread %s entered critical section.%n", name);

                    System.out.printf("Thread %s performing work.%n", name);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Thread %s finished working.%n", name);
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }
        executor.execute(new Worker("ThdA"));
        executor.execute(new Worker("ThdB"));
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
    }
}
