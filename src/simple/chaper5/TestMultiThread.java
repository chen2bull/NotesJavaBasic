/**
 * Java_chaper5
 * 
 * TestMultiThread.java
 * 2011-11-19 下午12:57:37
 * 
 * 
 */

/**第五课多线程
 * @author Chen Mingjian  2011-11-19
 * 
 */
public class TestMultiThread {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        //实现多线程的方法1：从Thread类继承 
//        TestThread.test();
        
        //实现多线程的方法1：实现一个Runable接口
//        TestThread2.test();
        
        //实现一个模拟火车票售票系统 
//        TicketsSystem.test();
        
        //测试等待队列 
//        TestQueue.test();
        
        //使用一个标志变量和interrupt方法去终止一个线程 
        TestEnd1.test();
        
    }

}

//实现多线程的方法1：从Thread类继承 Start
class TestThread {
    public static void test() {
        MyThread mt = new MyThread();
        mt.setPriority(Thread.MAX_PRIORITY);    //设置mt线程的优先级为最高,注意，Java的线程通常是抢占式的。高优先级的线程将抢占运行的时间（但这不是绝对的）。
        mt.setPriority(Thread.MIN_PRIORITY);    //设置mt线程的优先级为最低
//        mt.setDaemon(true);     //设置一个后台线程（又称守护进程）。特点：主线程结束的时候，后台线程也停止执行！
        mt.start();
        int index = 0;
        while(true) {
            System.out.println( "Main:"+Thread.currentThread().getName() );
            if(index++ > 9000) {
                break;
            }
        }
        
    }
    
}

class MyThread extends Thread {
    @Override
    public void run() { //重写run方法中的代码，会在别一个线程被执行
        super.run();
        while(true) {
            System.out.println("MyThread:"+getName());
            yield();    //这个方法使用当前线程暂停
        }
    }
}

//实现多线程的方法1：从Thread类继承 End

//实现多线程的方法2：实现一个Runable接口 Start 比方法1要好一点？
class TestThread2 {
    public static void test() {
        MyThread2 mt = new MyThread2();
        Thread aThread = new Thread(mt);
        aThread.start();
        
        while(true) {
            System.out.println( "Main:"+Thread.currentThread().getName() );
        }
    }
}
class MyThread2 implements Runnable {
    public void run() {
        while (true) {
            System.out.println( Thread.currentThread().getName() );
            
        }
    }
}
//实现多线程的方法2：实现一个Runable接口 End

//实现一个模拟火车票售票系统 Start
class TicketsSystem {
    public static void test() {
        SellThread st = new SellThread();
        Thread aThread1 = new Thread(st);
        Thread aThread2 = new Thread(st);
        Thread aThread3 = new Thread(st);
        Thread aThread4 = new Thread(st);
        
        aThread1.start();
        Thread.yield();
        aThread2.start();
        aThread3.start();
        aThread4.start();
        
    }
}

class SellThread implements Runnable {
    int tickets = 300;
    Object object = new Object();
    public void run() {
        while(true) {
/*            synchronized (object) {     //同步块的方式,synchronized同步关键字，为线程加锁
                if( tickets > 0 ) {
                    try {
                        Thread.sleep(10); //为了使问题重现
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println( Thread.currentThread().getName()+"sell tickets:"+tickets );
                    tickets--;
                } else {
                    break;
                }                
            }*/
            
            //同步方法的方式,给this对象加锁？  
            sell();
        }
    }
    //同步方法的方式  start
    public synchronized  void sell() {
        while( tickets > 0 ) {
            try {
                Thread.sleep(10); //为了使问题重现
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println( Thread.currentThread().getName()+"sell tickets:"+tickets );
            tickets--;
        }   
    }
    //同步方法的方式  end
}
//实现一个模拟火车票售票系统 End


//测试等待队列 start
class TestQueue {
    public static void test() {
        MyQueue queue = new MyQueue();
        Producer p = new Producer(queue);
        Consumer c = new Consumer(queue);
        p.start();
        c.start();
    }
}

class MyQueue {
    int value;
    boolean bFull = false;
    public synchronized void put(int i) {   //使用notify方法和wait方法的函数必须声明为synchronized
        //注意，这里put和get使用的是同一个同步对象（都声明为同步函数的形式）。对于wait和notify方法，这是一个必须的条件
        if(!bFull) {
            value = i;
            bFull = true;
            notify();       //放置数据的一方先通知
        }
        try {
            wait();         //   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized int get() {
        if(!bFull) {
            try {
                wait();     //获取数据的一方先等待
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bFull = false;
        notify();           //
        return value;
    }
}

class Producer extends Thread {
    MyQueue queue;
    public Producer(MyQueue q) {
        this.queue = q;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.put(i);
            System.out.println("Producer put:" + i);
        }
    }
}

class Consumer extends Thread {
    MyQueue queue;
    public Consumer(MyQueue q) {
        this.queue = q;
    }
    public void run() {
        while (true) {
            System.out.println("Consumer get " + queue.get());
        }
    }
}

//测试等待队列 end

//使用一个标志变量和interrupt方法去终止一个线程 start
class TestEnd1 {
    public static void test() {
        EndThread1 t1 = new EndThread1();
        int index = 0;
        t1.start();
        while (true) {
            if(index++ == 5000) {
               t1.stopThread();
               break;
            }
            System.out.println( Thread.currentThread().getName() );
        }
        System.out.println("main() exit");
    }
}

class EndThread1 extends Thread {
    private boolean bStop = false;
    public synchronized void run() {
        while (!bStop) {
            try {
                wait();
            } catch (InterruptedException e) {
                //当wait被调用的情况下：如果要终止这个线程，必须调用interrupt方法，否则这个线程会一直处于等待状态。
                //当线程的interrupt方法被调用的时候，wait会抛出一个异常，通过处理这个异常就可以终止(wait被调用的）线程。
//                e.printStackTrace();
                if(bStop) {
                    System.out.println("EndThread1 end");
                    return;
                }
            }
            
            System.out.println(getName());
        }
    }
    
    public void stopThread() {
        bStop = true;
        interrupt();        //在这里调用interrupt比在外部调用完这个函数后调用interrupt要好多了
    }
}
//使用一个标志变量和interrupt方法去终止一个线程 end