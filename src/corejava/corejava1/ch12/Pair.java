/**
 * 
 */
package corejava.corejava1.ch12;

/**
 * @version 1.00 2004-05-10
 * @author Cay Horstmann
 */
public class Pair<T> {
    /* 泛型类可以看作普通类的工厂。
     * 类型变量使用大写形式，且比较短，这是常用的写法。
     * java库的代码中，使用E表示集合元素类型。
     * K和V分别表示表的Key和Value。
     * T(需要时还可以用临近的字母U和S)表示任意类型。
     */
    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T newValue) {
        first = newValue;
    }

    public void setSecond(T newValue) {
        second = newValue;
    }

    private T first;
    private T second;
}