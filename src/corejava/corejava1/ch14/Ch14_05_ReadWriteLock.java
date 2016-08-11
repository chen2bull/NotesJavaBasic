/**
 *  @author Chen Mingjian  creat on 2012-9-19
 *
 */
package corejava.corejava1.ch14;

import java.util.concurrent.locks.*;

/**
 * A bank with a number of bank accounts that uses locks for serializing access.
 * 
 * @version 1.30 2004-08-01
 * @author Cay Horstmann
 */

public class Ch14_05_ReadWriteLock {
    public static void main(String[] args) {
        final int NACCOUNTS = 100;
        final double INITIAL_BALANCE = 1000;
        Ch14_05_ReadWriteLock b = new Ch14_05_ReadWriteLock(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++) {
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }

    /**
     * Constructs the bank.
     * 
     * @param n
     *            the number of accounts
     * @param initialBalance
     *            the initial balance for each account
     */
    public Ch14_05_ReadWriteLock(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        //
        // 1.构造一个读写锁对象
        bankLock = new ReentrantReadWriteLock();

        // 2.抽取读锁和写锁
        readLock = bankLock.readLock();     // 多个读操作可以共用读锁，但会排斥写操作
        writeLock = bankLock.writeLock();   // 写锁排斥其它的读操作和写操作

        // 3.构造写的条件对象（必要时还可以同时构造读的）
        sufficientFunds = writeLock.newCondition();
    }

    /**
     * Transfers money from one account to another.
     * 
     * @param from
     *            the account to transfer from
     * @param to
     *            the account to transfer to
     * @param amount
     *            the amount to transfer
     */
    public void transfer(int from, int to, double amount) throws InterruptedException {
        // 以下A、B、C、D 四个地方是同步存取的经典代码结构
        // 获得锁，使用与当前线程共享代码的线程不能执行下面的代码
        // 对所有修改者加写锁
        writeLock.lock();            // A
        try {                               // B
            // 在余额不足的情况下，当前线程被阻塞，并放弃锁。希望另一个线程可以进行增加账户余额。
            while (accounts[from] < amount) {   // B
                sufficientFunds.await();        // B
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            // 注意下面一行不会立即激活等待线程。它仅仅解除等待线程的阻塞，
            // 以便这些线程可以 在当前线程退出同步方法后，通过竞争实现对对象的访问。
            sufficientFunds.signalAll();    // C
        } finally {             // D
            // 把解锁方在finally子句内是至关重要的。如果在临界区的代码抛出异常，锁必须被释放
            // 否则，其他线程将永远阻塞
            writeLock.unlock();  // D
        }
    }

    /**
     * Gets the sum of all account balances.
     * 
     * @return the total balance
     */
    public double getTotalBalance() {
        // transfer方法中执行过这个方法
        // 锁是可以重入的，因为线程可以重复地获得已经持有的锁。
        // 锁保持一个持有计数器。lock多少次，也必须unlock才能解锁。
        // 对所有访问者加读锁
        readLock.lock();
        try {
            double sum = 0;

            for (double a : accounts)
                sum += a;

            return sum;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Gets the number of accounts in the bank.
     * 
     * @return the number of accounts
     */
    public int size() {
        return accounts.length;
    }

    private final double[]         accounts;
    private ReentrantReadWriteLock bankLock;
    private Condition              sufficientFunds;
    private Lock                   readLock;
    private Lock                   writeLock;
}

class TransferRunnable implements Runnable {
    /**
     * Constructs a transfer runnable.
     * 
     * @param b
     *            the bank between whose account money is transferred
     * @param from
     *            the account to transfer money from
     * @param max
     *            the maximum amount of money in each transfer
     */
    public TransferRunnable(Ch14_05_ReadWriteLock b, int from, double max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    public void run() {
        try {
            while (true) {
                int toAccount = (int) (bank.size() * Math.random());
                double amount = maxAmount * Math.random();
                bank.transfer(fromAccount, toAccount, amount);
                Thread.sleep((int) (DELAY * Math.random()));
            }
        } catch (InterruptedException e) {
        }
    }

    private Ch14_05_ReadWriteLock bank;
    private int                   fromAccount;
    private double                maxAmount;
    private int                   DELAY = 10;
}
