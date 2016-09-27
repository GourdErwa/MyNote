/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package com.gourd.erwa.util.corejava.code;

/**
 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
 * <p>
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 *
 * @author wei.Li by 15/5/20 (gourderwa@163.com).
 */
public class LeetCode {
    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three1 = new ListNode(3);
        ListNode three2 = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five1 = new ListNode(5);
        ListNode five2 = new ListNode(5);
        ListNode six = new ListNode(6);

        one.next = two;
        two.next = three1;
        three1.next = three2;
        three2.next = four;
        four.next = five1;
        five1.next = five2;
        five2.next = six;

        printListNode(one);
        ListNode node = Solution.deleteDuplicates(one);
        //
        System.out.println("处理后");
        printListNode(node);

    }

    static void printListNode(ListNode head) {
        ListNode node = head;

        for (; node.next != null; node = node.next) {
            System.out.println(node.val);
        }
    }

}

class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

}

class Solution {

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        int temp = -1;

        ListNode node = head;
        ListNode nextNodeIsNull = null;
        for (; node.next != null; node = node.next) {
            temp = node.val;
            if (nextNodeIsNull != null) {
                nextNodeIsNull.next = node;
                nextNodeIsNull = null;
            }
        }
        return null;
    }
}
