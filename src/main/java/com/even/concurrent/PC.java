package com.even.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project Name: ai
 * Des:用Lock和Condition获得同步功能
 * 与之前的生产者消费者应用程序类似，不过，它用锁和条件替换了同步及等待/通知。
 * PC的main()方法初始化了类Shared，Producer以及Consumer。Shared实例
 * 被传递到Producer和Consumer的构造函数中，这些线程随后启动。
 * 默认主线程调用Producer和Consumer构造函数，由于Shared实例也producer和consumer线程所访问，该实例必须对这些线程可见。
 * 在每个Producer和Consumer中，通过把s声明成final来达到这个可见的目的。当然也可以将这个属性声明成volatile，
 * 但是volatile暗示会对这一属性进行额外的写操作，事实上s在初始化之后就不会改变，所以使用final就可以了。
 *
 * Shared构造函数中，lock=new ReentrantLock();创建了一个锁，并且通过condition = lock.newCondition();创建了一个与之关联的条件。
 * 生产者和消费者可以通过Lock getLock()方法访问这个锁。
 *
 * 当变量available为true时，生产者调用条件的await()方法等待available变为false。消费者在消费字符之后，发出信号给
 * 这个条件来唤醒生产者（使用while循环是为了防止假唤醒）
 *
 * 在离开循环之后，生产者线程记录下新的字符，将available赋值为true表明有一个新字符可被消费，同时发出信号给这个条件来唤醒一个
 * 等待中的消费者。最终它释放了锁并且退出了setSharedChar()方法。
 *
 *
 * <p>
 * Created by Even on 2019/4/23 14:21
 */
public class PC {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}

class Shared {
    private char c;
    private volatile boolean available;
    private final Lock lock;
    private final Condition condition;

    Shared() {
        available = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    Lock getLock() {
        return lock;
    }

    char getSharedChar() {
        lock.lock();
        try {
            while (!available) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            available = false;
            condition.signal();//唤醒一个等待中的线程
        } finally {
            lock.unlock();
            return c;
        }
    }

    void setSharedChar(char c) {
        lock.lock();
        try {
            while (available) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.c = c;
            available = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Producer extends Thread {
    private final Lock l;

    private final Shared s;

    Producer(Shared s) {
        this.s = s;
        l = s.getLock();
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            l.lock();
            s.setSharedChar(ch);
            System.out.println(ch + " produced by producer.");
            l.unlock();
        }
    }
}

class Consumer extends Thread {
    private final Lock l;
    private final Shared s;

    public Consumer(Shared s) {
        this.s = s;
        l = s.getLock();
    }

    @Override
    public void run() {
        char ch;
        do {
            l.lock();
            ch = s.getSharedChar();
            System.out.println(ch + " consumed by consumer.");
            l.unlock();
        } while (ch != 'Z');
    }
}