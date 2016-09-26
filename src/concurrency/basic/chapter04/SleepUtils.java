package concurrency.basic.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 4-4
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {  // 当被打断时,线程会抛出这个异常,抛出异常后,该线程的中断标志被清空
            System.out.println(Thread.currentThread().getName() + "throw an InterruptedException");
        }
    }
}
