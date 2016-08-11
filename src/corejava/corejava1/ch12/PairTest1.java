package corejava.corejava1.ch12;

/**
 * @version 1.00 2004-05-10
 * @author Cay Horstmann
 */
public class PairTest1 {
    public static void main(String[] args) {
        String[] words = { "Mary", "had", "a", "little", "lamb" };
        Pair<String> mm = ArrayAlg1.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class ArrayAlg1 {
    /**
     * 获得字符串数组的最小值和最大值组成的值对（Pair）。
     * 
     * @param a
     *            一个字符串数组
     * @return 最小值和最大值组成的值对,如果a为空或为null则返回null
     */
    public static Pair<String> minmax(String[] a) {
        if (a == null || a.length == 0)
            return null;
        String min = a[0];
        String max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0)
                min = a[i];
            if (max.compareTo(a[i]) < 0)
                max = a[i];
        }
        return new Pair<String>(min, max);
    }
}