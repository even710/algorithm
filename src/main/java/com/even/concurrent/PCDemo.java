package com.even.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Project Name: ai
 * Des: 使用BlockingQueue和ArrayBlockingQueue实现的生产者消费者应用程序，内容更简单，因为不需要处理同步。
 * BlockingQueue的put()和take()方法分别将一个对象放到阻塞队列中以及将一个对象从中移除。
 * 如果没有空间可以旋转对象了，put()方法会被阻塞住，如果这个队列空了，take()方法也会阻塞住。
 * <p>
 * Created by Even on 2019/4/23 16:59
 */
public class PCDemo
{
    public static void main(String[] args)
    {
        final BlockingQueue<Character> bq = new ArrayBlockingQueue<>(26);
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable producer = () ->
        {
            for (char ch = 'A'; ch <= 'Z'; ++ch)
            {
                try
                {
                    bq.put(ch);
                    System.out.printf("%c produced by " + "producer.%n", ch);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        executorService.execute(producer);
        Runnable consumer = () ->
        {
            char ch = '\0';
            do
            {
                try
                {
                    ch = bq.take();
                    System.out.printf("%c consumed by " + "consumer.%n", ch);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } while (ch != 'Z');
            executorService.shutdownNow();
        };
        executorService.execute(consumer);
    }
}
