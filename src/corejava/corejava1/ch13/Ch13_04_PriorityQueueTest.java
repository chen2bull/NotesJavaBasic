package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program demonstrates the use of a priority queue.
 * 
 * @version 1.00 2004-08-03
 * @author Cay Horstmann
 */
public class Ch13_04_PriorityQueueTest {
    public static void main(String[] args) {
        // 与TreeSet一样,一个优先级队列既可以保存实现了Comparable接口的类对象
        // 也可以保存PriorityQueue构造器中提供了比较器的对象
        PriorityQueue<GregorianCalendar> pq = new PriorityQueue<GregorianCalendar>();
        pq.add(new GregorianCalendar(1906, Calendar.DECEMBER, 9)); // G. Hopper
        pq.add(new GregorianCalendar(1815, Calendar.DECEMBER, 10)); // A.
                                                                    // Lovelace
        pq.add(new GregorianCalendar(1903, Calendar.DECEMBER, 3)); // J. von
                                                                   // Neumann
        pq.add(new GregorianCalendar(1910, Calendar.JUNE, 22)); // K. Zuse

        System.out.println("Iterating over elements...");
        for (GregorianCalendar date : pq)
            System.out.println(date.get(Calendar.YEAR));
        System.out.println("Removing elements...");
        while (!pq.isEmpty())
            System.out.println(pq.remove().get(Calendar.YEAR));
    }
}
