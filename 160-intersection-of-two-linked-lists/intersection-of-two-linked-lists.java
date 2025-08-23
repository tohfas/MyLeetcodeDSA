/*
Microsoft | Linked List | Two Pointers | Easy

Problem:
--------
Given the heads of two singly linked lists headA and headB, 
return the node at which the two lists intersect. 
If the two linked lists have no intersection, return null.

Constraints:
------------
- 1 <= m, n <= 3 * 10^4
- No cycles exist in the list
- Must preserve original list structure

Examples:
---------
Input: listA = [4,1,8,4,5], listB = [5,6,1,8,4,5] → Output: Node with val=8
Input: listA = [1,9,1,2,4], listB = [3,2,4] → Output: Node with val=2
Input: listA = [2,6,4], listB = [1,5] → Output: null
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

How to Think Brute Force:
-------------------------
- Compare every node in listA with every node in listB.
- If node references match → intersection found.
- Otherwise → no intersection.

Brute Force Code (Conceptual):
-----------------
public ListNode getIntersectionNodeBruteForce(ListNode headA, ListNode headB) {
    for (ListNode a = headA; a != null; a = a.next) {
        for (ListNode b = headB; b != null; b = b.next) {
            if (a == b) { // reference equality, not value
                return a;
            }
        }
    }
    return null;
}

Step-by-Step Complexity:
------------------------
1. Outer loop traverses listA → O(m).
2. Inner loop traverses listB for each node of listA → O(n).
3. Total = O(m * n).
4. Space = O(1).

Final Complexity:
-----------------
- Time = O(m * n) → too slow for 30,000 nodes each.
- Space = O(1).
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation:
------------
- Intersection depends on "reference equality".
- The extra nodes at the start of the longer list make them unaligned.
- If we align them, both pointers will meet at the intersection node.

Optimized Idea:
---------------
- Calculate lengths of both lists.
- Advance the longer list's head by (lenA - lenB).
- Traverse both lists together:
  - If nodes match → intersection.
  - If end reached → no intersection.

Best Approach (Two Pointers Trick):
-----------------------------------
- Use two pointers (pA, pB).
- Move both one step at a time.
- When one reaches the end → redirect it to the other list’s head.
- If intersection exists → they meet at the node.
- If no intersection → both end at null at same time.

Why It Works:
-------------
- Both pointers traverse exactly m+n steps.
- By switching, alignment is achieved without computing lengths.
- Elegant and O(1) space.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Two Pointers)
--------------------------------------
*/

public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode pA = headA;
        ListNode pB = headB;

        // Traverse until they meet or both become null
        while (pA != pB) {
            // If end reached, jump to the other list
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA; // either intersection node or null
    }
}

/*
Time Complexity:
---------------
- Each pointer traverses at most m+n nodes.
- Total = O(m+n).

Space Complexity:
----------------
- Only two pointers used.
- Total = O(1).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force O(m*n).
2. Mention length-difference method O(m+n).
3. Present two-pointer trick (best: O(m+n), O(1)).
4. Emphasize that intersection check is by reference, not value.
*/
