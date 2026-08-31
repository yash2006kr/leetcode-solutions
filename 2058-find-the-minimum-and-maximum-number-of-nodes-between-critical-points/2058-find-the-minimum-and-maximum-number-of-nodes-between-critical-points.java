/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int firstIdx = -1, prevIdx = -1;
        int minDist = Integer.MAX_VALUE, maxDist = -1;

        ListNode prev = head;
        ListNode cur = head.next;
        int idx = 1; // index of cur

        while (cur.next != null) {
            boolean isMax = cur.val > prev.val && cur.val > cur.next.val;
            boolean isMin = cur.val < prev.val && cur.val < cur.next.val;

            if (isMax || isMin) {
                if (firstIdx == -1) {
                    firstIdx = idx;
                } else {
                    minDist = Math.min(minDist, idx - prevIdx);
                }
                prevIdx = idx;
            }

            prev = cur;
            cur = cur.next;
            idx++;
        }

        if (firstIdx == -1 || firstIdx == prevIdx) {
            return new int[]{-1, -1};
        }

        maxDist = prevIdx - firstIdx;
        return new int[]{minDist, maxDist};
    }
}