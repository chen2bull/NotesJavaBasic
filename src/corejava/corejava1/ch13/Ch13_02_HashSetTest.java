package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program uses a set to print all unique words in System.in.
 * 
 * @version 1.10 2003-08-02
 * @author Cay Horstmann
 */
public class Ch13_02_HashSetTest {
    public static void main(String[] args) {
        // 散列集HashSet可以快速地查找所需要的对象(只有不关心集合中元素的顺序时才用它)
        Set<String> words = new HashSet<String>(); // HashSet implements Set
        long totalTime = 0;

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String word = in.next();
            long callTime = System.currentTimeMillis();
            words.add(word);
            callTime = System.currentTimeMillis() - callTime;
            totalTime += callTime;
        }

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++)
            System.out.println(iter.next());
        System.out.println(". . .");
        System.out.println(words.size() + " distinct words. " + totalTime + " milliseconds.");
    }
    // 通常将桶数设为预计元素个数的75%~150%
    // 通常将装填因子设为0.75(默认值)
}
