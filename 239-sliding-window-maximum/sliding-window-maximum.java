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

Logic:
------
- For each sliding window of size k:
  - Iterate over k elements and compute maximum.
- Store result for each window.

Code:
------
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

Step-by-step Time Complexity:
-----------------------------
- Outer loop runs (n-k+1) ~ O(n).
- Inner loop runs k times per window.
- Total = O(n*k).
- For n=10^5, k=10^5 → 10^10 ops (too slow).

Space Complexity:
-----------------
- Output array O(n-k+1).
- No extra structures → O(1).

Final:
------
- Time = O(n*k) ❌
- Space = O(1)
*/


/*
--------------------------------------
MOVING TO OPTIMIZATION
--------------------------------------

Observation:
------------
- Each window overlaps heavily with previous one.
- When sliding:
  - Remove 1 element (left).
  - Add 1 element (right).
- We should not recompute max over all k elements.

Optimized Idea:
---------------
- Use a Deque to store **indices of useful elements**.
- Maintain elements in decreasing order:
  - Front of deque always = index of max element.
  - Remove indices out of window (i-k).
  - Remove smaller elements from back.
- At each step, max = nums[dq.front()].

Why Deque?
----------
- Double-ended operations: push/pop from both sides in O(1).
- Each element is added & removed at most once → O(n).
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Deque with ArrayDeque)
--------------------------------------
*/

import java.util.*;

class SolutionDeque {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];

        int[] result = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>(); // stores indices, not values

        for (int i = 0; i < n; i++) {
            // Step 1: Remove indices out of current window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Step 2: Maintain decreasing order in deque
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }

            // Step 3: Add current index
            dq.offerLast(i);

            // Step 4: Add max to result once first window is formed
            if (i >= k - 1) {
                result[i - k + 1] = nums[dq.peekFirst()];
            }
        }

        return result;
    }
}

/*
Time = O(n)
- Each element pushed & popped once.

Space = O(k)
- Deque holds at most k indices.
*/


/*
--------------------------------------
FURTHER OPTIMIZED (Deque with Primitive Array)
--------------------------------------
- In Java, ArrayDeque has method overhead.
- We can implement our own deque using int[].
- This reduces overhead and improves runtime.
*/

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int[] deque = new int[n]; // custom deque, store indices
        int left = 0, right = 0; // deque boundaries

        for (int i = 0; i < n; i++) {
            // Step 1: Remove out-of-window elements
            if (left < right && deque[left] <= i - k) left++;

            // Step 2: Maintain decreasing order
            while (left < right && nums[deque[right - 1]] < nums[i]) {
                right--;
            }

            // Step 3: Add current index
            deque[right++] = i;

            // Step 4: Add result once window is formed
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque[left]];
            }
        }
        return result;
    }
}

/*
Time Complexity:
---------------
- O(n): each index added/removed once.

Space Complexity:
----------------
- O(k): custom deque array stores at most k indices.

--------------------------------------
Interview Tip:
--------------
1. Start with brute force O(n*k).
2. Transition: "Instead of rescanning k elements, keep useful indices in deque."
3. Show ArrayDeque version (clean for whiteboard).
4. Mention custom array-deque optimization in Java for performance-critical code.
*/
