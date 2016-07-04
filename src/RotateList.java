/**
 * Given a list, rotate the list to the right by k places, where k is non-negative.

 For example:
 Given 1->2->3->4->5->NULL and k = 2,
 return 4->5->1->2->3->NULL.
 * Created by 陈明键 on 2016/7/4.
 */
public class RotateList {

    /* Definition for singly-linked list.*/
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode curPos = this;
            while (curPos != null) {
                sb.append(curPos.val);
                sb.append(",");
                curPos = curPos.next;
            }
            return sb.toString();
        }
    }

    /* 这种写法不直观,丢弃 */
    public ListNode rotateRight(ListNode head, int k) {
        ListNode curPos = head;
        int listLen = 0;
        while (curPos != null) {
            listLen++;
            if (curPos.next == null) {
                curPos.next = head;
                break;
            } else {
                curPos = curPos.next;
            }
        }
        if (listLen == 0) {
            return head;
        }
        k = k % listLen;
        k = listLen - k;
        while (k > 0) {
            if (k == 1) {
                curPos = head;
                head = head.next;
                curPos.next = null;
            } else {
                head = head.next;
            }
            k--;
        }
        return head;
    }

    public static void main(String args[]) {
        int[] arr = {1,2,3,5,8,9,10,25};
        ListNode list = new RotateList().initList(arr);
        System.out.println(list);
        System.out.println(new RotateList().rotateRight2(list, 30));

        int[] arr2 = {9, 10, 11};
        list = new RotateList().initList(arr2);
        System.out.println(new RotateList().rotateRight2(list, 9));

    }

    public ListNode rotateRight2(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null)    // 开头加head.next的判断,简化后续代码需要考虑的问题
            return head;
        ListNode curPos = head;
        ListNode lastNode = head;
        int len = 1;    // 这里初始条件是len等于1
        while (curPos.next != null) {
            len ++;
            curPos = curPos.next;
        }

        int remain = len - k % len;
        while (remain > 1) {    // 这类循环退出条件写remain>1,避免每次判断都做减法,代码更简单,更直观
            lastNode = lastNode.next;
            remain --;
        }
        curPos.next = head;
        head = lastNode.next;
        lastNode.next = null;
        return head;
    }

    public ListNode initList(int arr[]) {
        ListNode head = new ListNode(0);
        ListNode curPos = head;
        for (int num : arr) {
            curPos.next = new ListNode(num);
            curPos = curPos.next;
        }
        return head.next;
    }
}
