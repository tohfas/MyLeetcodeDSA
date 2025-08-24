/*
Microsoft | Arrays + Monotonic Stack Pattern | Easy
--------------------------------------------------
Problem:
--------
You are given two distinct integer arrays nums1 and nums2, 
where nums1 is a subset of nums2.
For each element in nums1, find its "Next Greater Element" in nums2.
The "Next Greater Element" is the first greater element to the right 
in nums2. If none exists, return -1.

Pattern:
--------
This is a classic "Next Greater Element" problem, solved by
the Monotonic Stack technique.

Difficulty:
-----------
Easy
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   1. For each element in nums1:
      - Find its index in nums2 (O(n)).
      - From that index, scan forward until a greater element is found (O(n)).
      - If none exists, return -1.
   2. Store results in an output array.
   
   Why this is O(m*n):
   -------------------
   - Suppose nums1 length = m, nums2 length = n.
   - For each of the m elements, we may scan up to n in the worst case.
   - So total = O(m * n).
   
   Space:
   ------
   - Only answer array O(m).
   - No extra DS.
*/
/*
class SolutionBruteForce {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length]; // final result array

        // Iterate through nums1 elements
        for (int i = 0; i < nums1.length; i++) {
            int num = nums1[i];
            int idx = -1;

            // Step 1: Find the index of nums1[i] in nums2
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] == num) {
                    idx = j;
                    break;
                }
            }

            // Step 2: Look to the right in nums2 for the next greater element
            int nextGreater = -1;
            for (int j = idx + 1; j < nums2.length; j++) {
                if (nums2[j] > num) {
                    nextGreater = nums2[j];
                    break; // stop at first greater element
                }
            }

            // Step 3: Store result
            ans[i] = nextGreater;
        }
        return ans;
    }
}

/*
Time Complexity (Brute Force):
------------------------------
- Finding index of each nums1[i] in nums2: O(n).
- Scanning for next greater: O(n).
- For m elements: O(m * n).

Space Complexity:
-----------------
- O(1) extra, only answer array O(m).
*/


/* =========================================================
   OPTIMIZED APPROACH (Monotonic Stack + HashMap)
   =========================================================
   Thought Process:
   ----------------
   - Brute force repeats scanning nums2 for each nums1 element.
   - To optimize, preprocess nums2 once:
     * Traverse nums2.
     * Use a monotonic decreasing stack to find "Next Greater Element" for each number.
     * Store results in a HashMap: num -> nextGreater.
   - Then answer nums1 queries in O(1) each using the map.

   Why Stack + HashMap?
   ---------------------
   - Stack ensures we process each element in O(1) amortized time (push + pop once).
   - HashMap gives O(1) lookups for nums1 queries.
   - Tradeoff: Extra O(n) space, but time drops from O(m*n) → O(m+n).

   Pattern:
   --------
   This is the standard "Next Greater Element" pattern with a decreasing stack.
*/

import java.util.*;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map to store next greater of each element in nums2
        Map<Integer, Integer> map = new HashMap<>();
        // Stack will store elements in decreasing order
        Stack<Integer> stack = new Stack<>();

        // Step 1: Preprocess nums2 with stack
        for (int num : nums2) {
            // While current num is greater than top of stack,
            // it is the "next greater" for that stack top
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        // Remaining elements in stack have no next greater
        while (!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }

        // Step 2: Build answer for nums1
        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]); // direct O(1) lookup
        }
        return ans;
    }
}

/*
Time Complexity (Optimized):
----------------------------
- Preprocessing nums2 with stack: O(n) (each element pushed/popped once).
- Filling answers for nums1: O(m).
- Total = O(m + n).

Space Complexity (Optimized):
-----------------------------
- Stack: O(n).
- HashMap: O(n).
- Answer array: O(m).
- Overall: O(m + n).
*/


/* =========================================================
   INTERVIEW THINKING FLOW:
   =========================================================
   1. Brute Force → O(m*n).
      - Works but inefficient because we rescan nums2 repeatedly.

   2. Optimization Insight:
      - Each element’s next greater only depends on its nearest greater to the right.
      - Stack naturally supports "next greater" problems (monotonic decreasing order).
      - Use HashMap for quick queries of nums1.

   3. Tradeoffs:
      - Brute force saves space (O(1)) but is slow.
      - Stack+HashMap uses O(n) space but reduces time to O(m+n).
      - Given constraints (n ≤ 1000), both work, but optimized is best for interviews.
*/
