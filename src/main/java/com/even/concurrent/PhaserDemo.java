package com.even.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Project Name: ai
 * Des:phaser 弹性同步屏障
 * 默认主线程创建了一对runnable任务，每个任务报告它们自己开始运行的时间。在创建一个Phaser的实例之后，运行这些任务并
 * 等待全部任务到达该屏障。
 * Created by Even on 2019/4/22 11:30
 */
public class PhaserDemo {

    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));
        runTasks(tasks);
    }

    static void runTasks(List<Runnable> tasks) {
        final Phaser phaser = new Phaser(1);//register self
        //创建和启动线程
        for (final Runnable task : tasks) {
            phaser.register();//往这个phaser中添加一条尚未抵达的线程，同时返回phase值作抵达分类用，即抵达phase值
            Runnable r = () -> {
                try {
                    Thread.sleep(50 + (int) (Math.random() * 300));
                } catch (InterruptedException e) {
                    System.out.println("interrupted thread");
                }
                phaser.arriveAndAwaitAdvance();//记录到达并等待phaser前进，它会返回抵达phase值。所有的线程都到达屏障时才会启动。
                task.run();
            };
            Executors.newSingleThreadExecutor().execute(r);
        }
        //允许线程启动和注销
        phaser.arriveAndDeregister();
    }
}
