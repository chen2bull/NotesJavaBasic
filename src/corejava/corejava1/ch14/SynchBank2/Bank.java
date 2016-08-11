package corejava.corejava1.ch14.SynchBank2;

/**
 * A bank with a number of bank accounts that uses synchronization primitives.
 * 
 * @version 1.30 2004-08-01
 * @author Cay Horstmann
 */
public class Bank {
    /**
     * Constructs the bank.
     * 
     * @param n
     *            the number of accounts
     * @param initialBalance
     *            the initial balance for each account
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
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
    // 以下A、B、C 三个地方是同步存取的经典代码结构
    // A synchronized关键字
    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount)
            // B
            wait();                     // B
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
        notifyAll();                    // C
    }

    /**
     * Gets the sum of all account balances.
     * 
     * @return the total balance
     */
    public synchronized double getTotalBalance() {
        double sum = 0;

        for (double a : accounts)
            sum += a;

        return sum;
    }

    /**
     * Gets the number of accounts in the bank.
     * 
     * @return the number of accounts
     */
    public int size() {
        return accounts.length;
    }

    private final double[] accounts;
}
