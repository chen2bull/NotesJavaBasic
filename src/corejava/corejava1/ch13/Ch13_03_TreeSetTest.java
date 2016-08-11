/**
@version 1.10 2004-08-02
@author Cay Horstmann
 */
package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program sorts a set of item by comparing their descriptions.
 */
public class Ch13_03_TreeSetTest {
    public static void main(String[] args) {
        // TreeSet比HashSet有所改进.树集是一个有序的集合.
        // 可以以任意顺序将元素插入到集合中.在对集合进行遍历时,每个值将自动地按照排序后的顺序呈现
        SortedSet<Item> parts = new TreeSet<Item>();
        parts.add(new Item("Toaster", 1234));
        parts.add(new Item("Widget", 4562));
        parts.add(new Item("Modem", 9912));
        System.out.println(parts);

        // 使用Comparable接口有个局限性:不能动态地改变排序的规则
        // 可以用一个实现了Comparator接口的类作为TreeSet的参数,能够动态改变排序的比较器
        SortedSet<Item> sortByDescription = new TreeSet<Item>(new Comparator<Item>() {
            public int compare(Item a, Item b) {
                String descrA = a.getDescription();
                String descrB = b.getDescription();
                return descrA.compareTo(descrB);
            }
        });

        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}

/**
 * An item with a description and a part number.
 */
class Item implements Comparable<Item> {
    /**
     * Constructs an item.
     * 
     * @param aDescription
     *            the item's description
     * @param aPartNumber
     *            the item's part number
     */
    public Item(String aDescription, int aPartNumber) {
        description = aDescription;
        partNumber = aPartNumber;
    }

    /**
     * Gets the description of this item.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[descripion=" + description + ", partNumber=" + partNumber + "]";
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null)
            return false;
        if (getClass() != otherObject.getClass())
            return false;
        Item other = (Item) otherObject;
        return description.equals(other.description) && partNumber == other.partNumber;
    }

    public int hashCode() {
        return 13 * description.hashCode() + 17 * partNumber;
    }

    public int compareTo(Item other) {
        return partNumber - other.partNumber;
    }

    private String description;
    private int    partNumber;
}
