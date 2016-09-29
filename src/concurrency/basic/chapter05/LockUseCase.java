package concurrency.basic.chapter05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 10-1 锁简单的使用
 */
public class LockUseCase {
    public void lock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("do nothings");
        } finally {
            lock.unlock();
        }
    }
}
