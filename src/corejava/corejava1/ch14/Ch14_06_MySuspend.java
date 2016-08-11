/**
 *  @author Chen Mingjian  creat on 2012-9-25
 *
 */
package corejava.corejava1.ch14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @date 2012-9-25 上午12:34:56
 * @author Chen Mingjian
 * @version 1.0
 */
public class Ch14_06_MySuspend {

    public static void main(String[] args) {

    }
}

class MySuspend implements Runnable {
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (true) {  // 也可以是其它的条件
            // ......
            // 在这里添加这个线程正常执行时代码

            if (suspendRequested) {
                suspendLock.lock();
                try {
                    while (suspendRequested) {
                        suspendCondition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    suspendLock.unlock();
                }
            }

        }

    }

    public void requestSuspend() {
        suspendRequested = true;
    }

    public void resquestResume() {
        suspendRequested = false;
        suspendLock.lock();
        try {
            suspendCondition.signalAll();
        } finally {
            suspendLock.unlock();
        }
    }

    private volatile boolean suspendRequested = false;
    private Lock             suspendLock      = new ReentrantLock();
    private Condition        suspendCondition = suspendLock.newCondition();
}
