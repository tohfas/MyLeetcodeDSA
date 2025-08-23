/*
Microsoft | Array | Sliding Window + Deque | Hard

Problem:
--------
You are given an array of integers nums, and a sliding window of size k.
The window moves from left to right by one step.
At each position, return the maximum element in that window.

Examples:
---------
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]

Input: nums = [1], k = 1
Output: [1]

Constraints:
------------
1 <= nums.length <= 10^5
-10^4 <= nums[i] <= 10^4
1 <= k <= nums.length
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

How to Think Brute Force:
-------------------------
- For each window of size k:
  1. Scan all k elements.
  2. Find maximum.
- Store results for each window.

Brute Force Code:
-----------------
public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    for (int i = 0; i <= n - k; i++) {
        int maxVal = nums[i];
        for (int j = i; j < i + k; j++) {
            maxVal = Math.max(maxVal, nums[j]);
        }
        result[i] = maxVal;
    }
    return result;
}

Step-by-Step Complexity:
------------------------
1. Outer loop runs (n-k+1) times ~ O(n).
2. Inner loop runs k times for each window.
3. Total time = O(n * k).
4. Space = O(n-k+1) for results.

Final Complexity:
-----------------
- Time = O(n * k) (too slow for n=10^5, k=10^5 → 10^10 operations).
- Space = O(n).
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation:
------------
- Each window overlaps heavily with the previous one.
- When sliding:
  - We remove the leftmost element.
  - We add one new element at the right.
- We don’t need to recompute the entire max from scratch.

Optimized Idea (Deque):
-----------------------
- Use a Deque to store indices of useful elements.
- Maintain decreasing order of values inside Deque.
- For each new element nums[i]:
  1. Remove indices out of window (i - k).
  2. Remove smaller elements from back (not useful for future).
  3. Add current index to back.
  4. Front of deque = index of max element of current window.

Why Deque:
----------
- Efficient insertion/removal from both ends.
- Ensures O(1) amortized per element.
- Gives max of window in O(1).

Tradeoffs:
----------
- Brute force: easy but O(n*k).
- Deque: O(n), optimal for large input sizes.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Deque Approach)
--------------------------------------
*/

import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];

        int[] result = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Step 1: Remove indices out of current window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Step 2: Maintain decreasing order in deque
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }

            // Step 3: Add current element index
            dq.offerLast(i);

            // Step 4: Add max of current window to result
            if (i >= k - 1) { 
                result[i - k + 1] = nums[dq.peekFirst()];
            }
        }

        return result;
    }
}

/*
Time Complexity:
---------------
- Each element added once and removed once from deque → O(n).
- Collecting results also O(n).
- Total = O(n).

Space Complexity:
----------------
- Deque holds at most k elements → O(k).
- Result array O(n-k+1).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force O(n*k).
2. Transition: "Instead of rescanning k elements, we maintain candidates in a deque."
3. Present optimized O(n), O(k) solution.
4. Stress how deque always keeps maximum at front.
*/
