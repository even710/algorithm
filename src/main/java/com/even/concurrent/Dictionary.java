package com.even.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Project Name: ai
 * Des: 演示ReadWriteLock和ReentrantReadWriteLock下面应用程序，其线程产生单词定义的数目，而读线程持续随机地访问这些条目并打印出来。
 * <p>
 *     默认主线程首先创建单词和定义的字符串数组，由于它们得从匿名类中访问，因而定义成final。创建了一个存储单词及其定义条目的map之后，线程获取一个重入的读写锁并开始访问读写锁。
 *
 *     针对写线程的runnable现在被创建出来，其run()方法遍历了单词的数组，每次遍历都会锁住写锁，当此方法返回时，写线程就有了互斥
 *     的写锁，同时能够通过调用map的put方法来更新map。在输出一个标识添加单词的消息之后，写线程释放锁，然后睡眠1毫秒给
 *     操作其他任务的线程出现的机会，接下来，基于线程池的executor会调用写线程的runnable。
 *
 *     针对读线程的runnable后续会被创建出来，其run()方法会重复地获取读锁，随机访问map中的条目，输出
 *     这个条目，然后释放读锁。接下来，从线程池中获取的executor会调用读线程的runnable。
 * <p>
 * Created by Even on 2019/4/23 15:17
 */
public class Dictionary {
    public static void main(String[] args) {
        final String[] words = {"hypocalcemia",
                "prolixity",
                "assiduous",
                "indefatigable",
                "castellan"};
        final String[] definitions = {"a deficiency of calcium in the blood",
                "unduly prolonged or drawn out",
                "showing great care, attention, and effort",
                "able to work or continue for a lengthy time without tiring",
                "the govenor or warden of a castle or fort"};
        final Map<String, String> dictionary = new HashMap<>();
        ReadWriteLock rwl = new ReentrantReadWriteLock(true);
        final Lock rlock = rwl.readLock();//读锁
        final Lock wlock = rwl.writeLock();//写锁
        Runnable writer = () -> {
            for (int i = 0; i < words.length; i++) {
                wlock.lock();
                try {
                    dictionary.put(words[i], definitions[i]);
                    System.out.println("writer storing " + words[i] + " entry");
                } finally {
                    wlock.unlock();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("writer interrupted");
                }
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(writer);
        Runnable reader = () -> {
            while (true) {
                rlock.lock();
                try {
                    int i = (int) (Math.random() * words.length);
                    System.out.println("read accessing " + words[i] + ": " + dictionary.get(words[i]) + " entry");
                } finally {
                    rlock.unlock();
                }
            }
        };
        es = Executors.newFixedThreadPool(1);
        es.submit(reader);
    }


}
