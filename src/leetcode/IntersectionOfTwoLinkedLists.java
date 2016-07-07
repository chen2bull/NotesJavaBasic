package leetcode;

import java.util.HashSet;

/**
 * Created by 陈明键 on 2016/7/4.
 */
public class IntersectionOfTwoLinkedLists {
    /* Definition for singly-linked list. */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode aPos = headA;
        ListNode bPos = headB;

        // 如果A和B在intersection前的长度不同,循环必然在第二次遍历结束
        while (aPos != bPos) {
            aPos = aPos == null ? headB : aPos.next;
            bPos = bPos == null ? headA : bPos.next;
        }
        return aPos;
    }
}
