package leetcode;

import java.util.List;

/**You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

 Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 Output: 7 -> 0 -> 8
 * Created by Administrator on 2016/7/3.
 */
public class AddTwoNumbers {

    /* Definition for singly-linked list.*/
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 使代码简化的地方就在这里,由于curPos会随着循环而改变
        // 为了索引到它,在开始的地方new一个对象,head和curPos都指向这个对象
        // 最后再返回head.next即可!!!!
        ListNode head = new ListNode(0);
        ListNode curPos = head;
        int carry = 0;
        int sum;

        while (l1 != null || l2 != null || carry != 0) {
            // 注意,这里的三元运算符是关键
            sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            carry = sum / 10;
            sum = sum % 10;
            curPos = curPos.next = new ListNode(sum);
            l1 = (l1 == null ? null : l1.next);
            l2 = (l2 == null ? null : l2.next);
        }
        return head.next;
    }
}
