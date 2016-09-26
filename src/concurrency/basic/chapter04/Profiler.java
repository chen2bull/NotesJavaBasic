package concurrency.basic.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 4-15 ThreadLocal(即线程变量)的使用
 * 每个线程都有一个这个变量,在第一次get的时候调用initialValue
 */
public class Profiler {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
                                                                protected Long initialValue() {
                                                                    return System.currentTimeMillis();
                                                                }
                                                            };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }
}
