/**
 *  @author Chen Mingjian  creat on 2012-9-18
 *
 */
package corejava.corejava1.ch14;

public class Ch14_01_MutiThead {
    public static void main(String args[]) {
        ARunner r = new ARunner();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);  // 多个线程可以共享代码(也可以共享数据)
        t1.start();
        t2.start();

        BThread t3 = new BThread();
        t3.start();

        for (int i = 0; i < 30; i++) {
            String s = Thread.currentThread().getName();
            System.out.println(s + ":" + i);
        }
    }
}

class ARunner implements Runnable { // 方法一、实现Runnable接口（注：有两个n）
    public void run() {
        for (int i = 0; i < 30; i++) {
            String s = Thread.currentThread().getName();
            System.out.println(s + ":" + i);
        }
    }
}

class BThread extends Thread {          // 方法二、继承Thread接口
    public void run() {
        for (int i = 0; i < 30; i++) {
            System.out.println("No." + i);
        }
    }
}
