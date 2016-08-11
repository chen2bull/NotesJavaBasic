package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program demonstrates the random shuffle and sort algorithms.
 * 
 * @version 1.10 2004-08-02
 * @author Cay Horstmann
 */
public class Ch13_06_CollectionsTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 49; i++)
            numbers.add(i);
        Collections.shuffle(numbers); // 混排
        List<Integer> winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination); // 排序
        System.out.println(winningCombination);

        // 数组转集合
        Integer[] integers = { new Integer(1997), new Integer(1999) };
        List<Integer> anList = new LinkedList<Integer>(Arrays.asList(integers));
        System.out.println(anList);
        // 集合转数组
        Integer[] integers2 = numbers.toArray(new Integer[numbers.size()]);
        for (Integer integer : integers2) {
            System.out.print(integer + ", ");
        }

        // 如果要由多个纯种访问集合
        // 采用视图机制来确保常规集合的线程安全
        // Map<String, Employee> map = Collections.synchronizedMap(new
        // HashMap<String, Employee>());

        /*
         * 常用算法 Collections.shuffle Collections.sort
         * Collections.binarySearch(c,element[,comparator]) //二分查找,必须用在已经排好序的集合中
         * Collections.addAll(c, elements) Collections.nCopies(n, o)
         * //n个值为o的List Collections.fill(list, obj) //将列表中所有位设置为相同的值
         * Collections.copy(dest, src) Collections.min(coll)
         * //求最小值,可以用Comparator作为第二个参数 Collections.max(coll) //求最大值
         * Collections.swap(list, i, j) //交换 Collections.reverse(list) //逆置
         * Collections.rotate(list, d) //循环移动,将下标为i的元素移动到(i + d)%list.size()
         * Collections.frequency(c, o) //返回c中与o相同的元素个数 Collections.disjoint(c1,
         * c2) //如果两个集合没有相同的元素,返回true
         */
    }
}
