package com.even.concurrent;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Project Name: ai 信号量
 * Des: 默认主线程创建一个资源池，一个反复获取和归还资源的runnable和一组executors。每个执行者
 * 都会执行这一runnable。
 * 类Pool的String getItem()以及void putItem(String item)方法获取和归还基于字符串的资源。在通过getItem()获取一个条目
 * 之前，调用线程必须从信号量中获取一个许可证，这样才能保证这个条目可用。当线程处理完这个条目，它会去调用putItem(String)方法，该方法会将此
 * 条目归还到池中，之后会释放一个针对这个信号量的许可证。这样，便可让其他的线程获取到这个条目。
 * 特别注意：String getNextAvailableItem()和boolean markAsUnused(String item)方法会同步地去维护资源池的一致性，但是，acquire()方法
 * 被调用的时候并不会持有同步锁，因为那样会阻止条目被归还到池中。这里面信号量把对资源池限制访问和对需要维护资源池一致性的同步操作都独立封装起来并分开处理了。
 * Created by Even on 2019/4/22 10:51
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        final Pool pool = new Pool();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                try {
                    while (true) {
                        String item;
                        System.out.println(name + " acquiring " + (item = pool.getItem()));
                        Thread.sleep(200 + (int) (Math.random() * 100));
                        System.out.println(name + " putting back " + item);
                        pool.putItem(item);
                    }
                } catch (InterruptedException e) {
                    System.out.println(name + "interrupted");
                }
            }
        };
        ExecutorService[] executors = new ExecutorService[Pool.MAX_AVAILABLE + 1];//构造一个Pool.MAX_AVAILABLE + 1大小的线程池。
        for (int i = 0; i < executors.length; ++i) {
            executors[i] = Executors.newSingleThreadExecutor();
            executors[i].execute(r);
        }
    }

    static final class Pool {
        public static final int MAX_AVAILABLE = 10;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);//构造一个公平策略的信号器。
        private final String[] items;//线程所需要的资源
        private final boolean[] used = new boolean[MAX_AVAILABLE];

        public Pool() {
            items = new String[MAX_AVAILABLE];
            /*填入信号量*/
            for (int i = 0; i < items.length; i++) {
                items[i] = "I" + i;
            }
        }

        /**
         * @return
         * @throws InterruptedException
         */
        String getItem() throws InterruptedException {
            /*获取许可证*/
            available.acquire();
            return getNextAvailableItem();
        }

        void putItem(String item) {
            /*标记item，释放许可证*/
            if (markAsUnused(item))
                available.release();
        }

        private synchronized String getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null;
        }

        private synchronized boolean markAsUnused(String item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (Objects.equals(item, items[i])) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else return false;
                }
            }
            return false;
        }

    }
}
