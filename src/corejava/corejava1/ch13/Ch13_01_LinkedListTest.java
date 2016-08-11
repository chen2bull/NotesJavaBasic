package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program demonstrates operations on linked lists.
 * 跟踪这个程序的流程可以观察迭代器的行为.下面的位置示意图帮助理解. |ACE A|CE AB|CE ... |BDFG B|DFG BD|FG B|FG
 * ...
 * 
 * @version 1.10 2004-08-02
 * @author Cay Horstmann
 */
public class Ch13_01_LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<String>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<String>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        // merge the words from b into a
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        while (bIter.hasNext()) {
            if (aIter.hasNext())
                aIter.next();
            aIter.add(bIter.next());
        }

        System.out.println(a);

        // remove every second word from b
        bIter = b.iterator();
        while (bIter.hasNext()) {
            bIter.next(); // skip one element
            if (bIter.hasNext()) {
                bIter.next(); // skip next element
                bIter.remove(); // remove that element
            }
        }

        System.out.println(b);

        // bulk operation: remove all words in b from a
        a.removeAll(b);

        System.out.println(a);
    }
}
