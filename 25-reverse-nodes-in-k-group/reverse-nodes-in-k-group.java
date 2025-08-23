/*
Microsoft | Linked List | Reversal Pattern | Hard

Problem:
--------
Reverse nodes in k-group. 
If the number of nodes is not a multiple of k, the remaining nodes stay as-is.
You may not change node values, only rearrange nodes.

Examples:
---------
Input: head=[1,2,3,4,5], k=2 → Output=[2,1,4,3,5]
Input: head=[1,2,3,4,5], k=3 → Output=[3,2,1,4,5]

Constraints:
------------
- 1 <= k <= n <= 5000
- 0 <= Node.val <= 1000
*/


/*
--------------------------------------
BRUTE FORCE (Explained Only)
--------------------------------------

Logic:
------
- For each group of k nodes:
  1. Collect nodes in a temporary list/array.
  2. Reverse that list.
  3. Reconnect back to main list.
- If less than k nodes remain, stop.

Complexity:
-----------
- Time: O(n) (every node visited once).
- Space: O(k) (temporary storage for each group).
- Not optimal since we use extra memory.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Iterative In-Place Reversal)
--------------------------------------

Logic:
------
1. Count the total number of nodes.
2. Use a dummy node to simplify handling of head changes.
3. For each group of size k:
   - Reverse nodes in-place using pointer manipulation.
   - Connect reversed group back to main list.
4. Continue until fewer than k nodes remain.

Why Best:
---------
- Avoids extra storage (reverses in-place).
- Iterative → O(n) time, O(1) space.
*/

class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Dummy node to handle cases where head changes
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;

        // Step 1: Count length of list
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        // Step 2: Reverse groups of k
        while (length >= k) {
            curr = prevGroupEnd.next;      // start of current group
            ListNode nextNode = curr.next; // node after current

            // Reverse k nodes in-place
            for (int i = 1; i < k; i++) {
                curr.next = nextNode.next;
                nextNode.next = prevGroupEnd.next;
                prevGroupEnd.next = nextNode;
                nextNode = curr.next;
            }

            // Move prevGroupEnd to end of this group
            prevGroupEnd = curr;
            length -= k;
        }

        return dummy.next;
    }
}

/*
Time Complexity:
---------------
- Counting length = O(n).
- Each node visited/reversed once = O(n).
- Total = O(n).

Space Complexity:
----------------
- Only constant pointers used → O(1).

Interview Prep Recap:
---------------------
1. Brute force: collect + reverse → O(n), O(k).
2. Optimization: reverse in-place using pointers → O(n), O(1).
3. Tradeoff: recursion is cleaner but uses O(n/k) stack space.
*/
