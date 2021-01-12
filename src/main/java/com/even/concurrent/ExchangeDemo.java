package com.even.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Project Name: ai
 * Des:交换器的例子，两个线程间交换对象
 * Created by Even on 2019/4/19 16:38
 */
public class ExchangeDemo {
    final static Exchanger<DataBuff> exchanger = new Exchanger<>();//定义交换器
    final static DataBuff initialEmptyBuffer = new DataBuff();
    final static DataBuff initialFullBuffer = new DataBuff("I");

    public static void main(String[] args) {
        class FillingLoop implements Runnable {
            int count = 0;

            @Override
            public void run() {
                DataBuff currentBuffer = initialEmptyBuffer;
                try {
                    while (true) {
                        addToBuffer(currentBuffer);
                        String name = Thread.currentThread().getName();
                         /*当满了就交换*/
                        if (currentBuffer.isFull()) {
                            System.out.println(name + ": filling thread wants to exchange");

                            currentBuffer = exchanger.exchange(currentBuffer);
                            System.out.println(name + ": filling thread receives exchange");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("filling thread interrupted");
                }
            }

            void addToBuffer(DataBuff buff) {
                String item = "NI" + count++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": Adding: " + item);
                buff.add(item);
            }
        }

        class EmptyLoop implements Runnable {

            @Override
            public void run() {
                DataBuff currentBuffer = initialFullBuffer;
                try {
                    while (true) {
                        takeFromBuffer(currentBuffer);
                        String name = Thread.currentThread().getName();
                        /*当没有就交换*/
                        if (currentBuffer.isEmpty()) {
                            System.out.println(name + ": emptying thread wants to exchange");
                            currentBuffer = exchanger.exchange(currentBuffer);
                            System.out.println(name + ": emptying thread receives exchange");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("emptying thread interrupted");
                }

            }

            void takeFromBuffer(DataBuff buffer) {
                String name = Thread.currentThread().getName();
                System.out.println(name + ": taking: " + buffer.remove());
            }
        }
        new Thread(new EmptyLoop()).start();
        new Thread(new FillingLoop()).start();
        new Thread(new FillingLoop()).start();
    }


    static class DataBuff {
        private final static int MAXITEMS = 10;
        private final List<String> items = new ArrayList<>();


        DataBuff() {
        }

        DataBuff(String prefix) {
            for (int i = 0; i < MAXITEMS; i++) {
                String item = prefix + i;
                System.out.printf("Adding %s%n", item);
                items.add(item);
            }
        }

        synchronized void add(String s) {
            if (!isFull())
                items.add(s);
        }

        synchronized boolean isEmpty() {
            return items.size() == 0;
        }

        synchronized boolean isFull() {
            return items.size() == MAXITEMS;
        }

        synchronized String remove() {
            if (!isEmpty())
                return items.remove(0);
            return null;
        }
    }
}
