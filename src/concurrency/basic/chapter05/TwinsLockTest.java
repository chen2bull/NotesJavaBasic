/**
 * 
 */
package concurrency.basic.chapter05;

import java.util.concurrent.locks.Lock;

import concurrency.basic.chapter04.SleepUtils;

/**
 * 10-11 测试同时访问
 */
public class TwinsLockTest {
    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
}
