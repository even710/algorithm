package com.even.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Project Name: ai 同步屏障
 * Des:默认主线程首先创建一个浮点数矩阵并把这个矩阵导出到标准输出流当中。主线程然后初始化了类Solver，这个类为每一行分别创建一条线程进行计算。更改之后的矩阵随后也被导出。
 * Solver提供了一个构造函数，它接收矩阵参数并用属性data指向这个矩阵，同时用属性N指向矩阵行的数目。这个构造函数之后创建一个拥有N条线程的同步屏障，并且又负责把所有的行合并到矩阵。
 * 最后，该构造函数分别创建工作线程，负责处理矩阵中单一的行。
 * 之后构造函数等待所有工作线程结束。
 * 工作线程的run()方法反复在指定的行上调用processRow()方法直到done()方法返回true。在processRow()执行过一次之后，done()就会返回true。在processRow()返回之后，
 * 也就意味着矩阵行已经被处理了，当前工作线程就会于同步屏障之上调用所有await()方法，它也就无法执行下去了。
 * 等于某些时候后，所有的工作线程都已经调用await()方法，它就会触发屏障动作来将所有处理过的矩阵行合并到最终的矩阵当中。
 * Created by Even on 2019/4/19 15:52
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        float[][] matrix = new float[3][3];
        int counter = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = counter++;
            }
        }
        dump(matrix);
        System.out.println();
        Solver solver = new Solver(matrix);
        System.out.println();
        dump(matrix);
    }

    static void dump(float[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + "");
            }
            System.out.println();
        }
    }

    static class Solver {
        final int N;
        final float[][] data;
        final CyclicBarrier barrier;

        class Worker implements Runnable {
            int myRow;
            boolean done = false;

            public Worker(int row) {
                this.myRow = row;
            }

            boolean done() {
                return done;
            }

            void processRow(int myRow) {
                System.out.println("Processing row:" + myRow);
                for (int i = 0; i < N; i++)
                    data[myRow][i] *= 10;
                done = true;
            }

            @Override
            public void run() {
                while (!done()) {
                    processRow(myRow);
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }

        public Solver(float[][] matrix) {
            this.data = matrix;
            N = matrix.length;
            barrier = new CyclicBarrier(N, new Runnable() {//当同步屏障中所有的线程都等待后会调用该线程
                @Override
                public void run() {
                    mergeRows();
                }
            });
            for (int i = 0; i < N; i++) {
                new Thread(new Worker(i)).start();
            }
            waitUntilDone();
        }

        void mergeRows() {
            System.out.println("merging");
            synchronized ("abc") {
                "abc".notify();
            }
        }

        void waitUntilDone() {
            synchronized ("abc") {
                try {
                    System.out.println("main thread waiting");
                    "abc".wait();
                    System.out.println("main thread notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("main thread interrupted");
                }
            }
        }
    }
}
