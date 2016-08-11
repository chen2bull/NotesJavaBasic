package corejava.corejava1.ch14;

import java.io.FileNotFoundException;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @date 2012-9-18 下午11:36:16
 * @author Chen Mingjian
 * @version 1.0
 */
public class Ch14_03_Daemon {
    public static void main(String args[]) {
        Thread t1 = new MyRunner(100);
        t1.setName("用户线程t1");
        // 用来设置线程的异常处理器，也可以用setDefaultUncaughtExceptionHandler为所有线程安装一个默认的处理器
        // 如果没有异常处理器，发生ThreadDeath时，什么也不做；
        // 如果没有异常处理器，发生ThreadDeath以外的异常时，线程的名字及栈踪迹被输出到System.err上.
        // （即同e.printStackTrace();一样）
        t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                System.err.println("捕捉到" + t.getName() + "一个异常" + e.getMessage());
            }
        });
        t1.start();

        Thread t2 = new MyRunner(100);
        t2.setDaemon(true);     // 设置线程为后台线程
        // 注：当其它非后台线程都结束的时候，后台线程也将立即结束
        // 后台线程永远不要访问文件和数据库等固有资源（它任何时候都有可能中止）
        t2.setName("后台线程t2");
        t2.start();
        t2.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.err.println("捕捉另一个线程" + t.getName() + "一个异常");
            }
        });

        for (int i = 0; i < 500; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
        System.out.println("主线程结束");
    }
}

class MyRunner extends Thread {
    private int n;

    public MyRunner(int n) {
        this.n = n;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            System.out.println(this.getName() + ":" + i);
            FileNotFoundException exception = new FileNotFoundException();
            // throw exception;
            // 取消上一行的注释，可以看到线程异常处理器的效果

        }
        System.out.println(this.getName() + ":" + "结束");
    }
}
